package ru.atc.fgislk.pup.tests;

import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.atc.fgislk.pup.testcore.controlprocessproxy.PupControlProcessProxy;
import ru.atc.fgislk.pup.testcore.pupcamunda.PupCamunda;
import ru.atc.fgislk.pup.testcore.taskdispatcher.PupTaskDispatcher;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;


public class HealthSubsystemsTests {
    private static final Logger LOG = LoggerFactory.getLogger(HealthSubsystemsTests.class);

    @Issue("FGISLKGEO-51")
    @TmsLink("les-33")
    @Parameters({"stend"})
    @Test(description = "Проверка health camunda-heartbeat-app", groups = {"health"})
    public void healthCamundaHeartbeat(@Optional("DEV") StendsDescriptionEnum stend) {
        LOG.info("Старт Проверка модуля исполнения процессов camunda-heartbeat-app");
        PupCamunda back = new PupCamunda(stend);
        back.healthStep();
    }
    @Parameters({"stend"})
    @Test(description = "Проверка liveness camunda-heartbeat-app", groups = {"health"})
    public void livenessCamundaHeartbeat(@Optional("DEV") StendsDescriptionEnum stend) {
        LOG.info("Старт Проверка liveness camunda-heartbeat-app");
        PupCamunda back = new PupCamunda(stend);
        back.liveness();
    }
    @Parameters({"stend"})
    @Test(description = "Проверка readiness camunda-heartbeat-app", groups = {"health"})
    public void readinessCamundaHeartbeat(@Optional("DEV") StendsDescriptionEnum stend) {
        LOG.info("Старт Проверка readiness camunda-heartbeat-app");
        PupCamunda back = new PupCamunda(stend);
        back.readiness();
    }

    @Issue("FGISLKGEO-53")
    @TmsLink("les-34")
    @Parameters({"stend"})
    @Test(description = "Проверка liveness модуля Диспетчер задач task-dispatcher", groups = {"health"})
    public void livenessTaskDispatcher(@Optional("DEV") StendsDescriptionEnum stend) {
        LOG.info("Старт Проверка функционирования модуля Диспетчер задач task-dispatcher");
        PupTaskDispatcher back = new PupTaskDispatcher(stend);
        back.livenessStep();
    }

    @Issue("FGISLKGEO-53")
    @TmsLink("les-34")
    @Parameters({"stend"})
    @Test(description = "Проверка liveness модуля Консоль управления control-process-proxy", groups = {"health"})
    public void livenessControlProcessProxy(@Optional("DEV") StendsDescriptionEnum stend) {
        LOG.info("Старт Проверка функционирования модуля Консоль управления control-process-proxy");
        PupControlProcessProxy back = new PupControlProcessProxy(stend);
        back.livenessStep();
    }

    @Issue("FGISLKGEO-53")
    @TmsLink("les-34")
    @Parameters({"stend"})
    @Test(description = "Проверка readiness модуля Диспетчер задач task-dispatcher", groups = {"health"})
    public void readinessTaskDispatcher(@Optional("DEV") StendsDescriptionEnum stend) {
        LOG.info("Старт Проверка работоспособности модуля Диспетчер задач task-dispatcher");
        PupTaskDispatcher back = new PupTaskDispatcher(stend);
        back.readinessStep();
    }

    @Issue("FGISLKGEO-53")
    @TmsLink("les-34")
    @Parameters({"stend"})
    @Test(description = "Проверка readiness модуля Консоль управления control-process-proxy", groups = {"health"})
    public void readinessControlProcessProxy(@Optional("DEV") StendsDescriptionEnum stend) {
        LOG.info("Старт Проверка работоспособности модуля Консоль управления control-process-proxy");
        PupControlProcessProxy back = new PupControlProcessProxy(stend);
        back.readinessStep();
    }

    @Parameters({"stend"})
    @Test(description = "Проверка health модуля task-dispatcher", groups = {"health"})
    public void healthTaskDispatcher(@Optional("DEV") StendsDescriptionEnum stend) {
        LOG.info("Старт Проверка health модуля task-dispatcher");
        PupTaskDispatcher back = new PupTaskDispatcher(stend);
        back.healthStep();
    }
    @Parameters({"stend"})
    @Test(description = "Проверка health модуля control-process-proxy", groups = {"health"})
    public void healthControlProcessProxy(@Optional("DEV") StendsDescriptionEnum stend) {
        LOG.info("Старт Проверка health модуля control-process-proxy");
        PupControlProcessProxy back = new PupControlProcessProxy(stend);
        back.healthStep();
    }

}
