package ru.atc.fgislk.pup.tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Optional;
import ru.atc.fgislk.pup.testcore.enums.ProcessNamesEnum;
import ru.atc.fgislk.pup.testcore.taskdispatcher.FetchAndLock;
import ru.atc.fgislk.pup.testcore.taskdispatcher.PupTaskDispatcher;
import ru.atc.fgislk.pup.testcore.enums.TestProcessStepsEnum;
import ru.atc.fgislk.shared.testcomponents.camunda.CamundaService;
import ru.atc.fgislk.shared.testcomponents.camunda.CamundaVariable;
import ru.atc.fgislk.shared.testcomponents.camunda.HistoryActivityInstance;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static ru.atc.fgislk.shared.testcomponents.Util.GSON;

public class TaskDispatcherTests {

    /**
     * Переменная определяет количество последовательно выполняемых процессов
     */
    private int seqCount = 1;
    //private final int TIME_WAIT = 0;

    private StendsDescriptionEnum stend;
    private PupTaskDispatcher td;

    private CamundaService camunda;

    private static final Logger LOG = LoggerFactory.getLogger(TaskDispatcherTests.class);

    @BeforeClass(groups = "init")
    @Parameters({"stend", "seqCount"})
    public void before(StendsDescriptionEnum stend, @Optional("1") int seqCount) {
        this.stend = stend;
        td = new PupTaskDispatcher(stend);
        camunda = new CamundaService(stend.getCamunda().getEngine());
        this.seqCount = seqCount;
    }

    @AfterClass(groups = "init")
    public void after() {
        td.close();

    }

    //@Parameters({"stend"})
    @Test(description = "les-41:154 [PUP] Доработка taskDispatcher для интеграции с ПВ", groups = {"sprint3"})
    public void les41() throws IOException, InterruptedException {
        LOG.info("les-41:154 [PUP] Доработка taskDispatcher для интеграции с ПВ");
        //старт процесса
        //String businessKey = "GEOTEST-20220701-55-51";//"GEOTEST-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMdd-HHmmssSSS"));
        String businessKey = "GEOTEST-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMdd-HHmmssSSS"));
        LOG.info("businesskey: {}", businessKey);
        CamundaVariable var1 = new CamundaVariable("startProcTime", "String", LocalDateTime.now().toString());
        List<FetchAndLock> msgList1 = startTestProcessStep(businessKey, var1);
        //проверим название запущенного процесса
        camunda.checkProcessName(businessKey, ProcessNamesEnum.GEO_TEST_1.getName());
        // проверим дошли в процессе до шага startPrpcess
        camunda.checkTaskByActivityName(businessKey, TestProcessStepsEnum.STATUS_STARTPROCESS.getActivityName());
        // проверим дошли в процессе до шага Подготовить задачу 1
        camunda.checkTaskByActivityName(businessKey, TestProcessStepsEnum.ST_PREPARE_TASK_1.getActivityName());
        // комплит сервистасков start-process и prepareTask1
        var1 = new CamundaVariable(msgList1.get(0).getMessage().getActivityId() + "CompleteTime", "String", LocalDateTime.now().toString());
        serviceTaskCompleteStep(msgList1.get(0), var1);
        //TimeUnit.SECONDS.sleep(TIME_WAIT);
        var1 = new CamundaVariable(msgList1.get(1).getMessage().getActivityId() + "CompleteTime", "String", LocalDateTime.now().toString());
        serviceTaskCompleteStep(msgList1.get(1), var1);
        //TimeUnit.SECONDS.sleep(TIME_WAIT);
        // проверим дошли в процессе до шага startPrpcess
        camunda.checkTaskByActivityName(businessKey, TestProcessStepsEnum.STATUS_SERVICE_STEP_1.getActivityName());
        // проверим дошли в процессе до шага Подготовить задачу 1
        camunda.checkTaskByActivityName(businessKey, TestProcessStepsEnum.UT_USER_TASK_1.getActivityName());
        //получим список сообщений для комплита сервис-таска "Сервис шага 1"
        msgList1.addAll(checkServiceTaskMsg(msgList1.get(0).getBusinessKey(), Arrays.asList(TestProcessStepsEnum.STATUS_SERVICE_STEP_1)));
        // комплит сервис-таска
        var1 = new CamundaVariable(msgList1.get(2).getMessage().getActivityId() + "CompleteTime", "String", LocalDateTime.now().toString());
        serviceTaskCompleteStep(msgList1.get(2), var1);
        //TimeUnit.SECONDS.sleep(TIME_WAIT);
        // комплит юзер-таска
        var1 = new CamundaVariable("ut1CompleteTime", "String", LocalDateTime.now().toString());
        CamundaVariable result = new CamundaVariable("result", "String", "Разрешить");
        // найдем в списке сообщений комплит сервис-таска prepareTask1
        userTaskComplete(msgList1.stream()
                .filter( p->
                        p.getMessage()
                                .getActivityId()
                                .equals(TestProcessStepsEnum.ST_PREPARE_TASK_1.getActivityId())).findFirst().get(), var1, result);
        //TimeUnit.SECONDS.sleep(TIME_WAIT);
        // получить сообщения для комплита сервистаска
        msgList1.addAll(td.getFetchAndLockMessages(msgList1.get(0).getBusinessKey(), Arrays.asList(TestProcessStepsEnum.ST_PREPARE_TASK_2)));
        // комплит сервис-таска
        var1 = new CamundaVariable("ut2CompleteTime", "String", LocalDateTime.now().toString());
        serviceTaskCompleteStep(msgList1.get(3), var1);
        //TimeUnit.SECONDS.sleep(TIME_WAIT);
        userTaskComplete(msgList1.get(3), var1);

        camunda.checkProcessEnded(businessKey);
        List<HistoryActivityInstance> historyList = camunda.checkHistoryActivityInstance(businessKey,
                TestProcessStepsEnum.START_EVENT,
                TestProcessStepsEnum.STATUS_STARTPROCESS,
                TestProcessStepsEnum.EXIT_1,
                TestProcessStepsEnum.INITIALIZE_TASK_DISPATCHER,
                TestProcessStepsEnum.ST_PREPARE_TASK_1,
                TestProcessStepsEnum.STATUS_SERVICE_STEP_1,
                TestProcessStepsEnum.EXIT_2,
                TestProcessStepsEnum.UT_USER_TASK_1,
                TestProcessStepsEnum.GATEWAY_1,
                TestProcessStepsEnum.ST_PREPARE_TASK_2,
                TestProcessStepsEnum.UT_USER_TASK_2,
                TestProcessStepsEnum.EXIT_3
                );
        LOG.info("тест les-41:154 [PUP] Доработка taskDispatcher для интеграции с ПВ завершен УСПЕШНО");
    }

    /**
     * Запустить бизнес-процесс
     * @param businessKey бизнескей
     * @param vars массив переменных
     * @return список сообщений для выполнения комплита сервистасков
     * @throws InterruptedException
     */
    @Step("Старт процесса")
    private List<FetchAndLock> startTestProcessStep(String businessKey, CamundaVariable ...vars) throws InterruptedException {
        //String businessKey = "GEOTEST-20220701-55-51";
        td.startProcess(ProcessNamesEnum.GEO_TEST_1, businessKey, vars);
        List<TestProcessStepsEnum> expectedSteps = new ArrayList<>();
        expectedSteps.add(TestProcessStepsEnum.STATUS_STARTPROCESS);
        expectedSteps.add(TestProcessStepsEnum.ST_PREPARE_TASK_1);
        // проверим в кафке наличие сообщений о коплите сервистасков. Их наличие говорит об успешном старте процесса
        List<FetchAndLock> msg = checkServiceTaskMsg(businessKey, expectedSteps);
        return msg;
    }

    @Step("Проверить сообщения о комплите сервистасков в kafka")
    private List<FetchAndLock> checkServiceTaskMsg(String businessKey, List<TestProcessStepsEnum> expectedSteps) throws InterruptedException {
        List<FetchAndLock> msg = td.getFetchAndLockMessages(businessKey, expectedSteps);
        List<String> actualActivityId = new ArrayList<>();
        msg.forEach(fal -> actualActivityId.add(fal.getMessage().getActivityId()));
        Collections.sort(actualActivityId);
        List<String> expectedActivityId = new ArrayList<>();
        expectedSteps.forEach(p -> expectedActivityId.add(p.getActivityId()));
        Collections.sort(expectedActivityId);
        for(FetchAndLock record: msg) {
            Allure.addAttachment("Сообщение из кафки", GSON.toJson(record));
        }

        Assert.assertEquals(actualActivityId, expectedActivityId, "Список сообщений в кафка о сервис-тасках не совпадает");
        return msg;
    }

    @Step("Комплит сервистаска")
    private void serviceTaskCompleteStep(FetchAndLock msg, CamundaVariable ... vars) {
        td.completeServiceTask(msg);
    }

    @Step("Комплит юзертаска")
    private void userTaskComplete(FetchAndLock msg, CamundaVariable ...vars) {
        td.completeUserTask(msg,vars);
        // проверим дошли в процессе до шага startPrpcess
    }

    /**
     * простой способ несколько раз выполнить тест готовка типа в BeforeClass
     *
     * @return
     */
    @DataProvider
    private Object[][] tdDataProvider()  {
        String bk = "GEOTEST-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMdd-HHmmss-"));
        Object [][] res = new Object[seqCount][1];
        for (int i = 0; i < seqCount; i++) {
            res[i][0] = bk + (i+1);
        }
        return res;
    }

    @Test(description = "Множество последовательных процессов", groups = {"pseudoLoad"}, dataProvider = "tdDataProvider")
    public void seqProcesses(String businessKey) throws IOException, InterruptedException {
        LOG.info("Множество последовательных процессов, положительный, запуск " + businessKey);
        //старт процесса
        CamundaVariable var1 = new CamundaVariable("startProcTime", "String", LocalDateTime.now().toString());
        List<FetchAndLock> msgList1 = startTestProcessStep(businessKey, var1);
        // комплит сервистасков start-process и prepareTask1
        var1 = new CamundaVariable(msgList1.get(0).getMessage().getActivityId() + "CompleteTime", "String", LocalDateTime.now().toString());
        serviceTaskCompleteStep(msgList1.get(0), var1);
        var1 = new CamundaVariable(msgList1.get(1).getMessage().getActivityId() + "CompleteTime", "String", LocalDateTime.now().toString());
        serviceTaskCompleteStep(msgList1.get(1), var1);
        //получим список сообщений для комплита сервис-таска "Сервис шага 1"
        msgList1.addAll(checkServiceTaskMsg(msgList1.get(0).getBusinessKey(), Arrays.asList(TestProcessStepsEnum.STATUS_SERVICE_STEP_1)));
        // комплит сервис-таска
        var1 = new CamundaVariable(msgList1.get(2).getMessage().getActivityId() + "CompleteTime", "String", LocalDateTime.now().toString());
        serviceTaskCompleteStep(msgList1.get(2), var1);
        //TimeUnit.SECONDS.sleep(TIME_WAIT);
        // комплит юзер-таска
        var1 = new CamundaVariable("ut1CompleteTime", "String", LocalDateTime.now().toString());
        CamundaVariable result = new CamundaVariable("result", "String", "Разрешить");
        // найдем в списке сообщений комплит сервис-таска prepareTask1
        userTaskComplete(msgList1.stream()
                .filter( p->
                        p.getMessage()
                                .getActivityId()
                                .equals(TestProcessStepsEnum.ST_PREPARE_TASK_1.getActivityId())).findFirst().get(), var1, result);
        //TimeUnit.SECONDS.sleep(TIME_WAIT);
        // получить сообщения для комплита сервистаска
        msgList1.addAll(td.getFetchAndLockMessages(msgList1.get(0).getBusinessKey(), Arrays.asList(TestProcessStepsEnum.ST_PREPARE_TASK_2)));
        // комплит сервис-таска
        var1 = new CamundaVariable("ut2CompleteTime", "String", LocalDateTime.now().toString());
        serviceTaskCompleteStep(msgList1.get(3), var1);
        //TimeUnit.SECONDS.sleep(TIME_WAIT);
        userTaskComplete(msgList1.get(3), var1);

        //camunda.checkProcessEnded(businessKey);
        LOG.info(businessKey + " завершен УСПЕШНО");
    }

}
