package ru.atc.fgislk.ppod.testcore.SendNotice;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.testng.annotations.Test;
import ru.atc.fgislk.ppod.testcore.Searcher;
import ru.atc.fgislk.ppod.testcore.utilsPpod.ConnectDB;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;
import ru.atc.fgislk.ppod.testcore.utilsPpod.FgislkKafkaProducerPpod;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class TestSendNotice {
    Map<String, String> configDocPackageRegistryDB = StendsDescriptionEnum.DEV.getPpodLk().getDocPackageRegistryBD(); // Package registry dataBase connect
    ConnectDB connectDocPackageRegistry = new ConnectDB(configDocPackageRegistryDB.get("path"),
            configDocPackageRegistryDB.get("login"),
            configDocPackageRegistryDB.get("password"),
            "file_set");

    String kafkaConfig = StendsDescriptionEnum.DEV.getKafka().getBootstrapServers();

    public TestSendNotice() throws SQLException {
    }

    @Test()
    public void testSendNotice() throws SQLException, InterruptedException {
        Searcher searcher = new Searcher();
        FgislkKafkaProducerPpod producer = new FgislkKafkaProducerPpod(kafkaConfig);

        String fileSetNo = connectDocPackageRegistry.getTheLastFileSetId();
        connectDocPackageRegistry.close();

        String kafka_correlationId = UUID.randomUUID().toString();
        String topicToSend = "ppod_notice";
        String topicToConsume = "ppod_pup";
        Map<String, String> headers = Map.of("receiver_code", "PPOD_NOTICE",
                "message_type", "REQUEST",
                "response_key", String.format("PPOD-%s", fileSetNo),
                "sender_code", "PPOD_PUP",
                "command", "notice:sending:request",
                "trace_key", String.format("PPOD-%s", fileSetNo),
                "response_command", "notice:sending:response",
                "kafka_correlationId", kafka_correlationId
        );
        System.out.println(headers);
        System.out.println(("kafka_correlationId for the test request is " + kafka_correlationId));
        String key = String.format("PPOD-%s", fileSetNo);

        System.out.println(("key for the test request is " + key));
        String message = String.format("{\"request\":{\"noticeId\":0,\"businessKey\":\"PPOD-%s\"," +
                "\"docPackageId\":\"%s\"," +
                "\"acceptDecision\":\"decline\"}}", fileSetNo, fileSetNo);
        String allureName = "Запрос на создание записи в ЖУВД";
//        producer.send(topicToSend, headers, key, message, allureName);

        ConsumerRecord<String, String> neededRecord = searcher.findMessageByHeaders(Map.of("kafka_correlationId", kafka_correlationId), topicToConsume);
        assert (neededRecord != null) : String.format("test failed, no message found" +
                " by kafka_correlationId %s",kafka_correlationId);
        searcher.printRecordFromKafka(neededRecord);
    }
}

