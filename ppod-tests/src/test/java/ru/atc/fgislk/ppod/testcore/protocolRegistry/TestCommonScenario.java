package ru.atc.fgislk.ppod.testcore.protocolRegistry;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import ru.atc.fgislk.ppod.testcore.protocolRegistry.dto.AddTaskRequest;
import ru.atc.fgislk.ppod.testcore.protocolRegistry.dto.AddTaskResultRequest;
import ru.atc.fgislk.ppod.testcore.protocolRegistry.dto.ProtocolCreateRequest;
import ru.atc.fgislk.ppod.testcore.protocolRegistry.dto.ProtocolCreateResponse;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;
import java.util.*;

import static io.restassured.RestAssured.given;
import static java.lang.Thread.sleep;

@Slf4j
public class TestCommonScenario {
    private static int protocolId;
    private static int taskId;
    private static final String[] validKinds = {"control", "approval"}; // for creating protocol
    private static final String[] validCriticalityTypes = {"fatal", "critical", "normal"}; // for adding task to protocol
    private static final String[] validResultsForTask = {"success", "fail", "skip"}; // for adding result to task


    public String randomElement(String[] validValues) {
        Random generator = new Random();
        int randomIndex = generator.nextInt(validValues.length);
        return validValues[randomIndex];
    }

    @Test(description = "POST create protocol", groups = {"sprint3"}, priority = 1)
        // creating protocol
    void createProtocol(@Optional("DEV") StendsDescriptionEnum stend) throws InterruptedException {
        Protocol protocol = new Protocol(stend);
        ProtocolCreateRequest protocolCreateRequest = ProtocolCreateRequest.builder()
                .processInstance("autoTest processInstance")
                .sourceSubsystem("PPOD")
                .kind(randomElement(validKinds))
                .build();
        Response response = protocol.postJson(EndPoints.postProtocol,
                "schemas/protocolRegistry/ProtocolCreateRequest.json",
                protocolCreateRequest,
                "Создание протокола");

        protocolId = response.getBody().as(ProtocolCreateResponse.class).getId();
        assert (response.statusCode() == 201) : "status code for POST create protocol is not 201, status code is " + response.statusCode();
        sleep(2000);
    }


    @Test(description = "POST get protocol", groups = {"sprint3"}, priority = 2)
    void getProtocol(@Optional("DEV") StendsDescriptionEnum stend) throws InterruptedException {
        Protocol protocol = new Protocol(stend);
        Response response = protocol.get(EndPoints.getProtocol, (Map.of("protocolId",
                        Integer.toString(protocolId))),
                null,
                "Получение данных о протоколе");
        assert (response.statusCode() == 200) : "status code for GET protocol is not 200, status code is " + response.statusCode();
        sleep(2000);
    }


    @Test(description = "POST add task to protocol", groups = {"sprint3"}, priority = 3)
    void addTaskToProtocol(@Optional("DEV") StendsDescriptionEnum stend) throws InterruptedException {
        // parameter "kind" in objectInfo is prioritized
        Map<String, String> objectInfo = new TreeMap<>((o1, o2) -> {
            if (o1.equals("kind")) {
                return 1;
            }
            return o1.compareTo(o2);
        });
        objectInfo.put("kind", "FileRef");
        objectInfo.put("link", "elvinTest.com");

        Task task = new Task(stend);

        AddTaskRequest addTaskRequest = AddTaskRequest.builder()
                .sourceSubsystem("PPOD")
                .controlRule("ppod_06")
                .criticality(randomElement(validCriticalityTypes))
                .objectInfo(objectInfo)
                .build();

        String url = EndPoints.postaddTask.replace("{protocolId}", Integer.toString(protocolId));
        Response response = task.postJson(url,
                null,
                null,
                addTaskRequest,
                "Добавление задачи к протоколу"
        );

        taskId = response.getBody().jsonPath().getInt("id");
        assert (response.statusCode() == 201) : "status code for POST add task to protocol is not 201, status code is " + response.statusCode();
        sleep(3000);
    }

    @Test(description = "PUT launch control check/add result to task", groups = {"sprint3"}, priority = 4)
    void addResultToTask(@Optional("DEV") StendsDescriptionEnum stend) throws InterruptedException {
        Map<String, String> details = Map.of("detailTitle", "detailDescription");
        TaskResult taskResult = new TaskResult(stend);
        AddTaskResultRequest addTaskResultRequest = AddTaskResultRequest.builder()
                .result(randomElement(validResultsForTask))
                .reason("reason value")
                .details(details)
                .sourceSubsystem("PPOD")
                .user("AutoTestUser")
                .build();

        String url = EndPoints.putTaskResult.replace("{protocolId}", Integer.toString(protocolId));
        url = url.replace("{taskId}", Integer.toString(taskId));
        Response response = taskResult.put(url,
                addTaskResultRequest,
                "Добавление результата для задачи");

        assert (response.statusCode() == 200) : "status code for PUT launch control check/add result to task" +
                " is not 200, status code is " + response.statusCode();
        sleep(3000);
    }

    @Test(description = "POST close protocol with 1 task", groups = {"sprint3"}, priority = 5)
    void closeProtocol(@Optional("DEV") StendsDescriptionEnum stend) {
        CloseProtocol closeProtocol = new CloseProtocol(stend);
        String url = EndPoints.postCloseProtocol.replace("{protocolId}", Integer.toString(protocolId));
        Response response = closeProtocol.postJson(url, null, "",
                "Закрытие протокола");
        // files to download
        // TODO: 7/11/2022  add  reportXmlSignFile when its not null
        String reportXmlFile = response.getBody().jsonPath().getString("reportXmlFile");
        String reportPrintFormFile = response.getBody().jsonPath().getString("reportPrintFormFile");
        assert (response.statusCode() == 200) : "status code for POST close protocol with 1 closed task " +
                "is not 200, status code is " + response.statusCode();
        System.out.println(reportPrintFormFile);
        System.out.println(reportXmlFile);
        // checking files are allowed to download
        // pdf
        Response responsePdf = given()
                .baseUri(StendsDescriptionEnum.DEV.getPpodLk().getFileStorage())
                .contentType(ContentType.JSON.withCharset("UTF-8"))
                .accept(ContentType.JSON)
                .when()
                .get("/" + reportPrintFormFile);
        assert (responsePdf.statusCode() == 200) : "status code for downloading pdf is not 200, status code is" + responsePdf.statusCode();
        byte[] biteArrayPdf = responsePdf.asByteArray();
        assert (biteArrayPdf.length != 0) : "Pdf report file is gonna be empty";
        log.info("PDF report is allowed for downloading");
        // xml without signature
        Response responseXml = given()
                .baseUri(StendsDescriptionEnum.DEV.getPpodLk().getFileStorage())
                .contentType(ContentType.JSON.withCharset("UTF-8"))
                .accept(ContentType.JSON)
                .when()
                .get("/" + reportXmlFile);
        assert (responseXml.statusCode() == 200) : "status code for downloading pdf is not w00, status code is" + responsePdf.statusCode();
        byte[] biteArrayXml = responsePdf.asByteArray();
        assert (biteArrayXml.length != 0) : "Pdf report file is gonna be empty";
        log.info("XML report without signature is allowed for downloading");
        }
    }
