package ru.atc.fgislk.ppod.testcore.integrityControl;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.testng.annotations.Test;
import ru.atc.fgislk.ppod.testcore.Searcher;
import ru.atc.fgislk.ppod.testcore.utilsPpod.ConnectDB;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;
import ru.atc.fgislk.ppod.testcore.utilsPpod.FgislkKafkaProducerPpod;
import java.util.UUID;
import java.sql.*;
import java.util.Map;

@Slf4j
public class TestIntegrityScenario {
    Map<String, String> configDocPackageRegistryDB = StendsDescriptionEnum.DEV.getPpodLk().getDocPackageRegistryBD(); // package registry dataBase connect
    Map<String, String> configDocPackageDB = StendsDescriptionEnum.DEV.getPpodLk().getDocPackageServiceBD(); // doc_package database connect
    ConnectDB connectFileSet = new ConnectDB(configDocPackageRegistryDB.get("path"),
            configDocPackageRegistryDB.get("login"),
            configDocPackageRegistryDB.get("password"),
            "file_set");
    ConnectDB connectDocPackageService = new ConnectDB(configDocPackageDB.get("path"),
            configDocPackageDB.get("login"),
            configDocPackageDB.get("password"));

    String kafkaConfig = StendsDescriptionEnum.DEV.getKafka().getBootstrapServers();

    public TestIntegrityScenario() throws SQLException {
    }

    @Test(groups = "draft")
    public void test() throws SQLException, InterruptedException {
        Searcher searcher = new Searcher();
        FgislkKafkaProducerPpod producer = new FgislkKafkaProducerPpod(kafkaConfig); // send request in kafka
        // taking file_set_no for the request in kafka
        String fileSetId = connectFileSet.getTheLastFileSetId();
        connectFileSet.close();

        String kafka_correlationId = UUID.randomUUID().toString();
        String topicToSend = "ppod_doc_package";
        String topicToConsume = "ppod_pup";
        Map<String, String> headers = Map.of("receiver_code", "PPOD_DOC_PACKAGE",
                "message_type", "REQUEST",
                "response_key", String.format("PPOD-%s", fileSetId),
                "sender_code", "PPOD_PUP",
                "command", "checkIntegrityRequest",
                "trace_key", String.format("PPOD-%s", fileSetId),
                "response_command", "checkIntegrityResponse",
                "kafka_correlationId", kafka_correlationId
        );
        log.info("kafka_correlationId for the test request is " + kafka_correlationId);
        String key = String.format("PPOD-%s", fileSetId);
        log.info("key for the test request is " + key);

        String message = String.format("{\n" +
                "  \"request\":{\n" +
                "    \"fileSetId\":\"%s\",\n" +
                "    \"processInstance\":\"PPOD-%s\"\n" +
                "  }\n" +
                "}", fileSetId, fileSetId);
        String allureName = "Запрос в кафку на проверку целостности/ data:put"; // запрос на прием документа
//        producer.send(topicToSend, headers, key, message, allureName);

        // check doc-package.fct_request_receive has a request with test key
        String query = "select * from fct_request_receive where request_key = '%s'";
        ResultSet resultSet = connectDocPackageService.executeQuery(String.format(query, key));

        assert (resultSet.next()) : "doc-package-service did not get a request for processing";
        connectDocPackageService.close();

        ConsumerRecord<String, String> neededRecord = searcher.findMessageByHeaders(Map.of("kafka_correlationId", kafka_correlationId), topicToConsume);
        assert (neededRecord != null) : String.format("test failed, no message found" +
                " by kafka_correlationId %s",kafka_correlationId);
        searcher.printRecordFromKafka(neededRecord);
    }
}
