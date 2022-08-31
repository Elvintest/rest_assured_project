package ru.atc.fgislk.pup.testcore.taskdispatcher;

import io.qameta.allure.Step;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.atc.fgislk.pup.testcore.enums.ProcessNamesEnum;
import ru.atc.fgislk.pup.testcore.enums.PupCommandEnum;
import ru.atc.fgislk.pup.testcore.enums.TestProcessStepsEnum;
import ru.atc.fgislk.shared.testcomponents.Util;
import ru.atc.fgislk.shared.testcomponents.back.CommonBackMethod;
import ru.atc.fgislk.shared.testcomponents.camunda.CamundaVariable;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;
import ru.atc.fgislk.shared.testcomponents.kafka.FgislkKafkaConsumer;
import ru.atc.fgislk.shared.testcomponents.kafka.FgislkKafkaProducer;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.util.Strings.isNullOrEmpty;
import static ru.atc.fgislk.shared.testcomponents.Util.GSON;
import static ru.atc.fgislk.shared.testcomponents.camunda.CamundaVariable.varsToString;

/**
 * Обеспечение процесса выполнения бизнес-процессов в камунде
 */
public class PupTaskDispatcher extends CommonBackMethod {
    private static final Logger LOGGGER = LoggerFactory.getLogger(PupTaskDispatcher.class);
    /**
     * количество минут ожидания в топике кафки требуемых сообщений
     */
    private static final int WAIT_KAFKA_MESSAGES = 2;
    /**
     * главный топик ПВ через который идет все взаимодействие
     */
    private static final String MAIN_ROUTER_TOPIC = "main_router_topic";

    /**
     * имя сервиса для которого создается развертывание, определялось при деплое тестового процесса
     */
    private static final String DEPLOYMENT_SOURCE = "PUP";//"geotest";
    /**
     * идентификатор Гео авто тесты
     */
    private static final String GEO_AT_SYSTEM = "geo_at";
    private static final String GROUP_ID = "geo_consumers";
    /**
     * топик, связанный с автотестами
     */
    private static final String TEST_TOPIC_NAME = "geo_at";

    private FgislkKafkaProducer kafkaProducer;

    private FgislkKafkaConsumer kafkaConsumer;

    public PupTaskDispatcher(StendsDescriptionEnum stend) {
        super(stend.getPup().getTaskDispatcherApp());
        kafkaProducer = new FgislkKafkaProducer(stend.getKafka().getBootstrapServers());
        kafkaConsumer = new FgislkKafkaConsumer(stend.getKafka().getBootstrapServers(), TEST_TOPIC_NAME, GROUP_ID);
    }

    public void close() {
        kafkaConsumer.close();
        kafkaProducer.close();
    }

    /**
     * Запустить тестовый процесс geo-test-process1 (ГЕО Тест 1 простой процесс)
     * <p>
     * Данный бизнес-процесс задеплоен в камунду как "replyTo": "geotest::cam-pup-01"
     *
     * @return идентификатор businessKey запущенного процесса
     */
    @Step("Отправить сообщение в кафку на запуск процесса")
    public String startProcess(ProcessNamesEnum proc, String businessKey, CamundaVariable... vars) {
        String bkStr = "\"businessKey\":\"" + businessKey + "\"";

        Map<String, String> headers = new TreeMap<>();
        headers.put("receiver_code", DEPLOYMENT_SOURCE);
        headers.put("sender_code", GEO_AT_SYSTEM);
        headers.put("command", PupCommandEnum.START_PROCESS.name());

        String listVars = varsToString(vars);
        String variables = "\"variables\":{" + listVars + "}";

        String message = "\"message\":{" + bkStr + "," + variables + "}";
        String json = "{" +
                Util.concat_ws(",", bkStr, message,
                        "\"typeRequest\":\"" + PupCommandEnum.START_PROCESS + "\"",
                        "\"processDefinitionKey\":\"" + proc.getKey() + "\"",
                        "\"replyTo\":\"\"") +
                "}";

        kafkaProducer.send(MAIN_ROUTER_TOPIC, headers, businessKey, json, "стартовое сообщение");
        return businessKey;
    }

    /**
     * метод читает сообщения в кафке о комплите сервис-тасков для тестового процесса
     *
     * @param businessKey
     * @return
     */
    private Map<String, FetchAndLock> readFetchAndLockMessages(String businessKey, List<TestProcessStepsEnum> serviceTasks) {
        ConsumerRecords<String, String> res = kafkaConsumer.poll();
        Map<String, FetchAndLock> messagesList = new TreeMap<>();
        for (ConsumerRecord<String, String> record : res) {
            if (!isCamundaMsg(record))
                continue;
            FetchAndLock message = GSON.fromJson(record.value(), FetchAndLock.class);
            if (message.getBusinessKey().equals(businessKey) &&
                    message.getTypeRequest().equals("FETCH_AND_LOCK") &&
                    serviceTasks.stream().anyMatch(elem -> elem.getActivityId().equals(message.getMessage().activityId)))
                messagesList.put(message.getMessage().getActivityId(), message);
        }
        return messagesList;
    }

    /**
     * метод проверяет по заголовкам сообщения, что оно является коплитом сервистаска от Camunda
     *
     * @param record
     * @return
     */
    private boolean isCamundaMsg(ConsumerRecord<String, String> record) {
        Map<String, String> headers = Util.getRecordHeaders(record);
        if (!headers.containsKey("trace_key") ||
                !headers.containsKey("message_type") ||
                !headers.containsKey("message_key") ||
                !headers.containsKey("sender_code"))
            return false;
        if (DEPLOYMENT_SOURCE.equals(headers.get("sender_code")) &&
                "REQUEST".equals(headers.get("message_type")))
            return true;
        return false;
    }

    /**
     * получить все сообщения из кафки для комплита требуемых сервистасков
     *
     * @param businessKey  идентификатор процесса
     * @param serviceTasks список ожидаемых сервистасков
     * @return список сообщений FETCH_AND_LOCK
     * @throws InterruptedException
     */
    public List<FetchAndLock> getFetchAndLockMessages(String businessKey, List<TestProcessStepsEnum> serviceTasks) throws InterruptedException {
        Map<String, FetchAndLock> messages = new TreeMap();
        LocalDateTime stopTime = LocalDateTime.now().plusMinutes(WAIT_KAFKA_MESSAGES);
        while (LocalDateTime.now().isBefore(stopTime) && messages.size() != serviceTasks.size()) {
            for (Map.Entry<String, FetchAndLock> msg : readFetchAndLockMessages(businessKey, serviceTasks).entrySet())
                messages.put(msg.getKey(), msg.getValue());
        }
        return messages.values().stream().collect(Collectors.toList());
    }

    public void completeServiceTask(FetchAndLock msg, CamundaVariable... vars) {
        String bkStr = "\"businessKey\":\"" + msg.getBusinessKey() + "\"";

        Map<String, String> headers = new TreeMap<>();
        headers.put("receiver_code", DEPLOYMENT_SOURCE);
        headers.put("sender_code", GEO_AT_SYSTEM);
        headers.put("command", PupCommandEnum.EXTERNAL_TASK_COMPLETE.name());

        CamundaVariable assigneeUser = new CamundaVariable("assigneeUser", "String", msg.getMessage().getId());
        List<CamundaVariable> varList = new ArrayList<>();
        varList.add(assigneeUser);
        for (CamundaVariable var : vars) {
            varList.add(var);
        }
        String listVars = varsToString(varList);
        String variables = "\"variables\":{" + listVars + "}";

        String message = "\"message\":{\"workerId\": \"ext-task-dispatcher\"," + variables + "}";
        String json = "{" +
                Util.concat_ws(",", bkStr, message,
                        "\"typeRequest\":\"" + PupCommandEnum.EXTERNAL_TASK_COMPLETE + "\"",
                        "\"idExternalTask\":\"" + msg.getMessage().getId() + "\"",
                        "\"replyTo\":\"" + msg.getReplyTo() + "\"") +
                "}";

        kafkaProducer.send(MAIN_ROUTER_TOPIC, headers, msg.getBusinessKey(), json, "комплит сервистаска " + msg.getMessage().getActivityId());
    }

    public void completeUserTask(FetchAndLock msg, CamundaVariable... vars) {
        String bkStr = "\"businessKey\":\"" + msg.getBusinessKey() + "\"";
        Map<String, String> headers = new TreeMap<>();
        headers.put("receiver_code", DEPLOYMENT_SOURCE);
        headers.put("sender_code", GEO_AT_SYSTEM);
        headers.put("command", PupCommandEnum.USER_TASK_COMPLETE.name());

        String listVars = varsToString(vars);
        String variables = "\"variables\":{" + listVars + "}";

        String message = "\"message\":{" + variables + "}";
        String json = "{" +
                Util.concat_ws(",", bkStr, message,
                        "\"typeRequest\":\"" + PupCommandEnum.USER_TASK_COMPLETE + "\"",
                        "\"idExternalPrepareTask\":\"" + msg.getMessage().getId() + "\"",
                        "\"replyTo\":\"" + msg.getReplyTo() + "\"") +
                "}";

        kafkaProducer.send(MAIN_ROUTER_TOPIC, headers, msg.getBusinessKey(), json, "комплит юзертаска " + msg.getMessage().getActivityId());
    }

}
