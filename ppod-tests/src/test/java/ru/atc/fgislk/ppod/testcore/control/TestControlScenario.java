package ru.atc.fgislk.ppod.testcore.control;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.testng.annotations.Test;
import ru.atc.fgislk.ppod.testcore.Searcher;
import ru.atc.fgislk.ppod.testcore.utilsPpod.ConnectDB;
import ru.atc.fgislk.ppod.testcore.utilsPpod.FgislkKafkaProducerPpod;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class TestControlScenario {
    Map<String, String> configDocPackageRegistryDB = StendsDescriptionEnum.DEV.getPpodLk().getDocPackageRegistryBD(); // Package registry dataBase connect
    ConnectDB connectFileSet = new ConnectDB(configDocPackageRegistryDB.get("path"),
            configDocPackageRegistryDB.get("login"),
            configDocPackageRegistryDB.get("password"),
            "file_set");

    String kafkaConfig = StendsDescriptionEnum.DEV.getKafka().getBootstrapServers();

    public TestControlScenario() throws SQLException {
    }

    @Test()
    public void testControl() throws SQLException, InterruptedException {
        Searcher searcher = new Searcher();
        FgislkKafkaProducerPpod producer = new FgislkKafkaProducerPpod(kafkaConfig);

//        String fileSetId = connectFileSet.getTheLastFileSetId();
//        connectFileSet.close();
        String fileSetId = "84835y34534534534534";

        String kafka_correlationId = UUID.randomUUID().toString();
        String topicToSend = "ppod_doc_package";
        String topicToConsume = "ppod_pup";
        Map<String, List<String>> headers = Map.of("receiver_code", List.of("PPOD_DOC_PACKAGE"),
                "message_type", List.of("REQUEST"),
                "response_key", List.of(String.format("PPOD-%s", fileSetId)),
                "sender_code", List.of("PPOD_PUP"),
                "command", List.of("checkFlcRequest"),
                "trace_key", List.of(String.format("PPOD-%s", fileSetId)),
                "response_command", List.of("checkFlcResponse"),
                "kafka_correlationId", List.of(kafka_correlationId)
        );
        System.out.println(headers);
        System.out.println(("kafka_correlationId for the test request is " + kafka_correlationId));
        String key = String.format("PPOD-%s", fileSetId);
        System.out.println(("key for the test request is " + key));

        String message = String.format("{\n" +
                "  \"request\": {\n" +
                "    \"docPackageId\": \"%s\",\n" +
                "    \"processInstance\": \"PPOD-%s\"\n" +
                "  }}", fileSetId, fileSetId);

        String allureName = "Запрос в кафку на флк";
        producer.send(topicToSend, headers, key, message, allureName);

        searcher.startTestAndCleanMainTopic();
        ConsumerRecord<String, String> neededRecord = searcher.findMessageByHeaders(Map.of("kafka_correlationId", kafka_correlationId), topicToConsume);
        assert (neededRecord != null) : String.format("test failed, no message found" +
                " by kafka_correlationId %s", kafka_correlationId);
        searcher.printRecordFromKafka(neededRecord);
    }
}
