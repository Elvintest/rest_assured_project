package ru.atc.fgislk.ppod.testcore.createRecordZUVD;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.testng.annotations.Test;
import ru.atc.fgislk.ppod.testcore.Searcher;
import ru.atc.fgislk.ppod.testcore.utilsPpod.ConnectDB;
import ru.atc.fgislk.ppod.testcore.utilsPpod.FgislkKafkaProducerPpod;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class TestCreateRecordZUVD {
    Map<String, String> configDocPackageRegistryDB = StendsDescriptionEnum.DEV.getPpodLk().getDocPackageRegistryBD(); // Package registry dataBase connect

    ConnectDB connectFileSet = new ConnectDB(configDocPackageRegistryDB.get("path"),
            configDocPackageRegistryDB.get("login"),
            configDocPackageRegistryDB.get("password"),
            "file_set");

    String kafkaConfig = StendsDescriptionEnum.DEV.getKafka().getBootstrapServers();


    public TestCreateRecordZUVD() throws SQLException {
    }


    @Test()
    public void testCreateRecordZUVD() throws SQLException, InterruptedException {
        Searcher searcher = new Searcher();
        FgislkKafkaProducerPpod producer = new FgislkKafkaProducerPpod(kafkaConfig); // send request in kafka

        String fileSetId = connectFileSet.getTheLastFileSetId(); // taking file_set_no for the request in kafka
        connectFileSet.close();

        String kafka_correlationId = UUID.randomUUID().toString();
        String topicToSend = "ppod_doc";
        String topicToConsume = "ppod_pup";
        Map<String, String> headers = Map.of("receiver_code", "PPOD_DOC",
                "message_type", "REQUEST",
                "response_key", String.format("PPOD-%s", fileSetId),
                "sender_code", "PPOD_PUP",
                "command", "doc:import:package",
                "trace_key", String.format("PPOD-%s", fileSetId),
                "response_command", "doc:import:accept",
                "kafka_correlationId", kafka_correlationId
        );
        System.out.println(headers);
        System.out.println(("kafka_correlationId for the test request is " + kafka_correlationId));
        String key = String.format("PPOD-%s", fileSetId);
        System.out.println(("key for the test request is " + key));

        String message = String.format("{\n" +
                "  \"request\": {\n" +
                "    \"number\": \"%s\",\n" +
                "    \"process\": \"PPOD-%s\"\n" +
                "  }}", fileSetId, fileSetId);
        String allureName = "Запрос на создание записи в ЖУВД";
//        producer.send(topicToSend, headers, key, message, allureName);

        ConsumerRecord<String, String> neededRecord = searcher.findMessageByHeaders(Map.of("kafka_correlationId", kafka_correlationId), "topicToConsume");
        assert (neededRecord != null) : String.format("test failed, no message found" +
                " by kafka_correlationId %s",kafka_correlationId);
        searcher.printRecordFromKafka(neededRecord);

    }
}
