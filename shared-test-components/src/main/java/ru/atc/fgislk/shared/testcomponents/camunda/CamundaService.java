package ru.atc.fgislk.shared.testcomponents.camunda;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.internal.collections.Pair;
import ru.atc.fgislk.shared.testcomponents.back.BaseRestService;
import ru.atc.fgislk.shared.testcomponents.camunda.*;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class CamundaService extends BaseRestService {

    /**
     * собственный сериализатор, из-за особенностей формата даты, возвращаемой камундой
     */
    private static final Gson CAMUNDA_GSON = new GsonBuilder()
            .registerTypeAdapter(OffsetDateTime.class, (JsonDeserializer<OffsetDateTime>)
                    (json, typeOfT, context) -> OffsetDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXXX")))
            .registerTypeAdapter(OffsetDateTime.class, (JsonSerializer<OffsetDateTime>)
                    (date, typeOfSrc, context) -> new JsonPrimitive(date.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
            .create();

    /**
     * Тип списка процессов
     */
    private static final Type LIST_PROCESS_INSTANCE_TYPE = new TypeToken<List<ProcessInstance>>() {
    }.getType();

    /**
     * Тип списка истории для процесса
     */
    private static final Type LIST_HISTORY_ACTIVITY_INSTANCE_TYPE = new TypeToken<List<HistoryActivityInstance>>() {
    }.getType();
    /**
     * Тип процесса из истории
     */
    private static final Type LIST_HISTORY_PROCESS_INSTANCE_TYPE = new TypeToken<List<HistoryProcessInstance>>() {
    }.getType();

    /**
     * Тип списка external-task
     */
    private static final Type LIST_EXTERNAL_TASK_TYPE = new TypeToken<List<ExternalTask>>() {
    }.getType();


    /**
     * Таймаут ожидания результатов запроса в секундах. Очень часто поиск результата происходит до того, как камунда отработает
     */
    private final int waitResultTimeout;

    /**
     * явная задержка перед отправкой повторных запросах при ожидании результата
     */
    private final int sleep;

    /**
     * конструктор с параметром
     *
     * @param hostCamunda адреса camunda engine
     */
    public CamundaService(String hostCamunda) {
        super(hostCamunda);
        waitResultTimeout = 60;
        sleep = 3000;
    }

    /**
     * залогировать и выполнить запрос без параметров
     *
     * @param path       путь
     * @param allureName название лога
     * @return пара(код ответа ; текст ответа)
     */
    private Pair<Integer, String> getStatusOk(String path, String allureName) {
        return getStatusOk(path, new TreeMap<>(), allureName);
    }

    /**
     * залогировать и выполнить запрос с параметрами
     *
     * @param path       путь
     * @param params     параметры
     * @param allureName название лога
     * @return пара(код ответа ; текст ответа)
     */
    private Pair<Integer, String> getStatusOk(String path, Map<String, String> params, String allureName) {
        Pair<Integer, String> response = get(path, params, allureName);
        Assert.assertEquals(response.first().intValue(), 200);
        return response;
    }

    /**
     * получить процесс по бизнескею. Если процессов несколько - то упадем
     *
     * @param businessKey бизнескей процесса
     * @return инстанс
     */
    private ProcessInstance getProcessInstance(String businessKey) throws InterruptedException {
        String path = "/process-instance";//?businessKey=" + businessKey;
        Map<String, String> params = new TreeMap<>();
        params.put("businessKey", businessKey);
        Pair<Integer, String> res = getStatusOk(path, params, "найти экземпляр процесса по businessKey");
        List<ProcessInstance> processInstances = CAMUNDA_GSON.fromJson(res.second(), LIST_PROCESS_INSTANCE_TYPE);
        LocalDateTime ldt = LocalDateTime.now().plusSeconds(waitResultTimeout);
        while (processInstances.isEmpty() && LocalDateTime.now().isBefore(ldt)) {
            res = getStatusOk(path, params, "найти экземпляр процесса по businessKey");
            processInstances = CAMUNDA_GSON.fromJson(res.second(), LIST_PROCESS_INSTANCE_TYPE);
            Thread.sleep(sleep);
        }
        Assert.assertEquals(processInstances.size(), 1, "По " + businessKey + " найдено " + processInstances.size() + " процессов");
        return processInstances.get(0);
    }

    /**
     * получить сведения о процессе в камунде метод /activity-instances
     *
     * @param businessKey бизнескей
     * @return сведения о процессе
     */
    private ActivityInstance getActivityInsatnce(String businessKey) throws InterruptedException {
        ProcessInstance processInstance = getProcessInstance(businessKey);
        String request = "/process-instance/" + processInstance.getId() + "/activity-instances";
        Pair<Integer, String> tmp = getStatusOk(request, "получить сведения о процессе");
        return CAMUNDA_GSON.fromJson(tmp.second(), ActivityInstance.class);
    }

    /**
     * проверить, что в шагах процесса присутствует требуемый. Если процессов по бизнескей несколько - возможен некорректный результат.
     *
     * @param businessKey бизнескей
     * @param task        Обычно перечисление, определяющее шаг(task) в камунде для подсистемы
     * @throws IOException
     * @throws InterruptedException
     */
    @Step("Проверить шаг процесса")
    public <T extends ICamundaTaskEnum> void checkTaskByActivityId(String businessKey, T task) throws IOException, InterruptedException {
        checkTaskByActivityId(businessKey, task.getActivityId());
    }

    /**
     * проверить, что в шагах процесса присутствует требуемый. Если процессов по бизнескей несколько - возможен некорректный результат.
     *
     * @param businessKey бизнескей
     * @param activityId  идентификатор таска
     */
    @Step("Проверить шаг процесса")
    public void checkTaskByActivityId(String businessKey, String activityId) throws InterruptedException {
        LocalDateTime stopTime = LocalDateTime.now().plusSeconds(waitResultTimeout);
        ActivityInstance activityInstance = getActivityInsatnce(businessKey);
        // пока шаги не содержат требуемого и не закончился таймаут
        while (activityInstance.getChildActivityInstances().stream().noneMatch(p -> p.getActivityId().equals(activityId)) &&
                LocalDateTime.now().isBefore(stopTime)) {
            Thread.sleep(sleep);
            activityInstance = getActivityInsatnce(businessKey);
        }
        Assert.assertTrue(activityInstance.getChildActivityInstances().stream().anyMatch(p -> p.getActivityId().equals(activityId)),
                "В шагах процесса отсутствует шаг c id=" + activityId);
    }

    /**
     * проверить, что в шагах процесса присутствует требуемый. Если процессов по бизнескей несколько - возможен некорректный результат.
     *
     * @param businessKey бизнескей
     * @param task        Обычно перечисление, определяющее шаг(task) в камунде для подсистемы
     * @throws IOException
     * @throws InterruptedException
     */
    @Step("Проверить шаг процесса")
    public <T extends ICamundaTaskEnum> void checkTaskByActivityName(String businessKey, T task) throws IOException, InterruptedException {
        checkTaskByActivityName(businessKey, task.getActivityName());
    }

    /**
     * проверить, что в шагах процесса присутствует требуемый. Если процессов по бизнескей несколько - возможен некорректный результат.
     *
     * @param businessKey  бизнескей
     * @param activityName название таска
     */
    @Step("Проверить шаг процесса")
    public void checkTaskByActivityName(String businessKey, String activityName) throws InterruptedException {
        LocalDateTime stopTime = LocalDateTime.now().plusSeconds(waitResultTimeout);
        ActivityInstance activityInstance = getActivityInsatnce(businessKey);
        // пока шаги не содержат требуемого и не закончился таймаут
        while (activityInstance.getChildActivityInstances().stream().noneMatch(p -> p.getActivityName().equals(activityName)) &&
                LocalDateTime.now().isBefore(stopTime)) {
            Thread.sleep(sleep);
            activityInstance = getActivityInsatnce(businessKey);
        }
        Assert.assertTrue(activityInstance.getChildActivityInstances().stream().anyMatch(p -> p.getActivityName().equals(activityName)),
                "В шагах процесса отсутствует шаг c name=" + activityName);
    }

    /**
     * Проверить название процесса по бизнескею. Если процессов по бизнескей несколько - возможен некорректный результат.
     *
     * @param businessKey     идентификатор броцесса
     * @param expectedProcess ожидаемый тип процесса, обычно перечисление процессов в подсистеме
     * @param <T>
     * @throws IOException
     * @throws InterruptedException
     */
    @Step("проверить название процесса по бизнескею")
    public <T extends ICamundaProcessEnum> void checkProcessName(String businessKey, T expectedProcess) throws IOException, InterruptedException {
        checkProcessName(businessKey, expectedProcess.getName());
    }

    /**
     * Проверить название процесса по бизнескею. Если процессов по бизнескей несколько - возможен некорректный результат.
     *
     * @param businessKey         идентификатор броцесса
     * @param expectedProcessName ожидаемое название процесса
     */
    @Step("проверить название процесса по бизнескею")
    public void checkProcessName(String businessKey, String expectedProcessName) throws InterruptedException {
        String definitionId = getProcessInstance(businessKey).getDefinitionId();
        Pair<Integer, String> res = getStatusOk("/process-definition/" + definitionId, "запрос общих сведений о процессе");
        ProcessDefinition pd = CAMUNDA_GSON.fromJson(res.second(), ProcessDefinition.class);
        Assert.assertEquals(pd.getName(), expectedProcessName);
    }


    /**
     * получить сведения из истории о выполнении процесса и сравнить с ожидаемыми шагами
     *
     * @param businessKey идентификатор процесса
     * @return список записей, содержащий выполненные шаги
     */
    @Step("проверить шаги в истории выполнения процесса")
    public <T extends ICamundaTaskEnum> List<HistoryActivityInstance> checkHistoryActivityInstance(String businessKey, T... steps) throws InterruptedException, IOException {
        String id = getHistoryProcessInstance(businessKey).getId();
        Pair<Integer, String> pair = getStatusOk("/history/activity-instance?processInstanceId=" + id, "найти все таски в которых был процесс");
        List<HistoryActivityInstance> results = CAMUNDA_GSON.fromJson(pair.second(), LIST_HISTORY_ACTIVITY_INSTANCE_TYPE);
        Assert.assertEquals(results.size(), steps.length, "Количество шагов в истории выполнения отличается от ожидаемого");
        for(T elem:steps) {
            Assert.assertTrue(results.stream().anyMatch(hist -> hist.getActivityId().equals(elem.getActivityId())), "Отсутствует шаг " + elem.getActivityId());
        }
        return results;
    }

    /**
     * Получить историю процесса. Если процессов по бизнескею много, то упадем
     *
     * @param businessKey businessKey
     * @return строка статуса процесса
     */
    private HistoryProcessInstance getHistoryProcessInstance(String businessKey) throws InterruptedException {
        Pair<Integer, String> pair = getStatusOk("/history/process-instance?processInstanceBusinessKey=" + businessKey, "получить процесс из истории");
        List<HistoryProcessInstance> lst = CAMUNDA_GSON.fromJson(pair.second(), LIST_HISTORY_PROCESS_INSTANCE_TYPE);
        LocalDateTime stopTime = LocalDateTime.now().plusSeconds(waitResultTimeout);
        while (lst.isEmpty() && LocalDateTime.now().isBefore(stopTime)) {
            Thread.sleep(sleep);
            pair = getStatusOk("/history/process-instance?processInstanceBusinessKey=" + businessKey, "получить процесс из истории");
            lst = CAMUNDA_GSON.fromJson(pair.second(), LIST_HISTORY_PROCESS_INSTANCE_TYPE);
        }
        Assert.assertEquals(lst.size(), 1);
        return lst.get(0);
    }

    /**
     * Проверить завершился ли процесс. Ожидает завершения в течении waitResultTimeout секунд
     *
     * @param businessKey идентификатор процесса
     */
    @Step("Проверка завершения процесса в camunda")
    public void checkProcessEnded(String businessKey) throws InterruptedException {
        HistoryProcessInstance processInstance = getHistoryProcessInstance(businessKey);
        LocalDateTime stopTime = LocalDateTime.now().plusSeconds(waitResultTimeout);
        while (processInstance.getEndTime() == null && LocalDateTime.now().isBefore(stopTime)) {
            Thread.sleep(sleep);
            processInstance = getHistoryProcessInstance(businessKey);
        }
        Assert.assertNotNull(processInstance.getEndTime());
    }

    /**
     * получить все процессы на шаге
     *
     * @param processDefinitionKey идентификатор типа процесса
     * @param activityId           шаг
     * @return список инстансов на шаге
     */
    public List<ProcessInstance> getInstanceList(String processDefinitionKey, String activityId) {
        Pair<Integer, String> pair = getNoLog("/process-instance?processDefinitionKey=" + processDefinitionKey + "&activityIdIn=" + activityId);
        return CAMUNDA_GSON.fromJson(pair.second(), LIST_PROCESS_INSTANCE_TYPE);
    }

    /**
     * получить значение переменной на юзер-таске
     *
     * @param processId processId
     * @param varName   varName
     * @return Значение переменной
     */
    public CamundaVariable getVariable(String processId, String varName) {
        Pair<Integer, String> pair = getNoLog("/execution/" + processId + "/localVariables/" + varName);
        return CAMUNDA_GSON.fromJson(pair.second(), CamundaVariable.class);
    }

    public List<ExternalTask> getExternalTask(String processInstanceId) {
        Pair<Integer, String> pair = getNoLog("/external-task?processInstanceId=" + processInstanceId);
        return CAMUNDA_GSON.fromJson(pair.second(), LIST_EXTERNAL_TASK_TYPE);
    }
}

