package ru.atc.fgislk.ppod.testcore.camundaPpod;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import ru.atc.fgislk.ppod.testcore.constants.ProcessDefinitionNames;
import ru.atc.fgislk.ppod.testcore.dto.ProcessDefinitionDto;
import ru.atc.fgislk.ppod.testcore.dto.ProcessInstanceDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;

/**
 * компонент для работы с камундой в модуле ППОД
 */
/*
deployment = поставка (может включать несколько процессов)
processDefinition = процесс
processInstance = экземпляр процесса
execution = поток выполнения
 */
public class Camunda {
    public RequestSpecification requestSpecification;
    public ResponseSpecification responseSpecification;

    public Camunda(String camundaUrl) {
        this.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(camundaUrl)
                .setContentType(ContentType.ANY)
                .setAccept(ContentType.JSON)
                .build();

        this.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public ProcessInstanceDto[] getAllProcessInstances() {
        Response response = given().spec(requestSpecification).when().get("/process-instance");

        return response.as(ProcessInstanceDto[].class);
    }

    /**
     * Возвращает массив инстансов, по одному businessKey могут быть разные инстансы в разных definitionId
     * @param businessKey
     * @return
     */
    public ProcessInstanceDto[] getProcessesInstancesByBusinessKey(String businessKey) {
        Response response = given().spec(requestSpecification).when().queryParams("businessKey", businessKey).get(("/process-instance/"));

        return response.as(ProcessInstanceDto[].class);
    }

    /**
     * Возвращает конкретный инстанс в конкретном процессе по defenitionId
     * @param businessKey
     * @param definitionId
     * @return
     */
    public ProcessInstanceDto getProcessesInstance(String businessKey, String definitionId) {
        Response response = given().spec(requestSpecification).when().queryParams("businessKey", businessKey).get(("/process-instance/"));
        ProcessInstanceDto[] processInstanceDtos = response.as(ProcessInstanceDto[].class);
        for (ProcessInstanceDto processInstanceDto : processInstanceDtos){
            if (Objects.equals(processInstanceDto.getDefinitionId(), definitionId)){
                return processInstanceDto;
            }
        }
        return null;
    }

    public ProcessDefinitionDto[] getAllDefinitions() {
        Response response = given().spec(requestSpecification).when().get("/process-definition");

        return response.as(ProcessDefinitionDto[].class);
    }

    /**
     * Получение уникального идентификатора процесса(выбирается последняя версия) по его title из ProcessDefinitionNames
     * @param ProcessDefinitionName
     * @return
     */
    public String getProcessDefinition(String ProcessDefinitionName) {
        ProcessDefinitionDto[] processDefinitionDtos = this.getAllDefinitions();
        List<ProcessDefinitionDto> validDefinitions = new ArrayList<>();
        for (ProcessDefinitionDto processDefinitionDto : processDefinitionDtos) {
            if (processDefinitionDto.getKey().equals(ProcessDefinitionName)) {
                validDefinitions.add(processDefinitionDto);
            }
        }
        int maxVersion = validDefinitions.get(0).getVersion();
        for (int i = 0; i < validDefinitions.size(); i++) {
            if (validDefinitions.get(i).getVersion() > maxVersion) {
                maxVersion = validDefinitions.get(i).getVersion();
            }
        }
        for (int i = 0; i < validDefinitions.size(); i++) {
            if (validDefinitions.get(i).getVersion() == maxVersion)
                return validDefinitions.get(i).getId();
        }

        return null;
    }
}
