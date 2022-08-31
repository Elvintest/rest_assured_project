import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.atc.fgislk.ppod.testcore.Searcher;
import ru.atc.fgislk.ppod.testcore.constants.Topics;
import ru.atc.fgislk.ppod.testcore.utilsPpod.FgislkKafkaProducerPpod;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;

import java.util.*;


public class Extraction {
    String kafkaConfig = StendsDescriptionEnum.DEV.getKafka().getBootstrapServers();

    @Test
    public static void main(String[] args) throws InterruptedException, JsonProcessingException {
//        String baseUrl = "http://camunda.pup.dev.fgislk.at-consulting.ru/engine-rest";


        Searcher searcher = new Searcher();
//        searcher.startTestAndCleanMainTopic();
        Extraction extraction = new Extraction();

        String kafka_correlationId = extraction.sendExtraction(Topics.MAIN_TOPIC);
        // проверяем, что сообщение доставлено для ppod в main_router_topic
        Thread.sleep(5000);
        ConsumerRecord<String, String> recordToPpod = searcher.findMessageByHeaders(Map.of("kafka_correlationId", kafka_correlationId,
                "receiver_code", Topics.PPOD.toUpperCase(Locale.ROOT),
                "message_type", "REQUEST"), Topics.MAIN_TOPIC);
        Assert.assertNotNull(recordToPpod, String.format("initial request didn't reach for main_router_topic, correlationId is %s", kafka_correlationId));
        System.out.println("record for ppod from LKL : \n");
        searcher.printRecordFromKafka(recordToPpod);
        //проверяем что в личный кабинет ушло сообщение об акцепте/реджекте заявления на выписку
        Thread.sleep(5000);
        String businessKey = null;
        ConsumerRecord<String, String> recordToLkl = searcher.findMessageByHeaders(Map.of("kafka_correlationId", kafka_correlationId,
                "receiver_code", Topics.LKL.toUpperCase(Locale.ROOT)), Topics.MAIN_TOPIC);
        Assert.assertNotNull(recordToLkl, String.format("accept/reject message was not sent back to LKL by %s", kafka_correlationId));
        for (Header header : recordToLkl.headers()) {
            if (header.key().equals("message_type")) {
                Assert.assertEquals(new String(header.value()), "ACCEPT", String.format("initial request was" +
                        " rejected by file-set-service," +
                        "the message_type is %s" +
                        " the kafka message:\n %s", new String(header.value()), recordToLkl.value()));
            }
        }
        String value = recordToLkl.value().toString();
        System.out.println("record from ppod to LKL:\n");
        System.out.println(value);
        try {
            businessKey = new JSONObject(value).getJSONObject("accept").getString("number");
        } catch (Exception exception) {
            System.out.println("the process by fileset-service didn't start cause not-accept message");
            System.out.println(exception.getMessage());
        }
        System.out.println("business key is " + businessKey);
        // fileSetId это businessKey
        String fileSetId = businessKey;
        String key = "PPOD-" + businessKey;
        // проверяем что сообщение ушло в пуп command=START_PROCESS и начался процесс предоставления выписки
        ConsumerRecord<String, String> recordToPup = searcher.findMessageByHeadersAndKey(key,Map.of("receiver_code","PUP_PPOD",
                "command","START_PROCESS"), Topics.MAIN_TOPIC);
        Assert.assertNotNull(recordToPup, "wasn't found the request Start process to PUP_PPOD \n");
        System.out.println("START_PROCESS:\n");
        searcher.printRecordFromKafka(recordToPup);
        /**
         * отсюда начинается процесс проверки целостности
         */

//        // проверяем что сообщение ушло в ppod для проверки целостности
//        ConsumerRecord<String, String> recordFromPupIntegrity = searcher.findMessageByHeadersAndKey(key,Map.of("receiver_code","PPOD",
//                "command","requestIntegrityControl"), Topics.MAIN_TOPIC);
//        Assert.assertNotNull(recordFromPupIntegrity, "wasn't found requestIntegrityControl from PUP to PPOD \n");
//        System.out.println("requestIntegrityControl\n");
//        searcher.printRecordFromKafka(recordFromPupIntegrity);
//
//        // проверяем что сообщение ушло от PPOD_REQUEST  к PPOD_PUP с command =extract:integrity:response.(проверка завершена)
//        ConsumerRecord<String, String> recordToPupIntegrity = searcher.findMessageByHeadersAndKey(key,
//                Map.of("message_type", "ACCEPT",
//                        "receiver_code",Topics.PPOD_PUP.toUpperCase(Locale.ROOT),
//                "command", "extract:integrity:response"), Topics.MAIN_TOPIC);
//        Assert.assertNotNull(recordToPupIntegrity, "wasn't found the request extract:integrity:response from PPOD_REQUEST to PPOD_PUP \n");
//        System.out.println("extract:integrity:response\n");
//        searcher.printRecordFromKafka(recordToPupIntegrity);
//        /**
//         * отсюда начинается процесс автопроверок
//         */
//        // проверяем что сообщение ушло в ppod для автопроверок
//        ConsumerRecord<String, String> recordFromPupControl = searcher.findMessageByHeadersAndKey(key,Map.of("receiver_code","PPOD",
//                "command","requestControl"), Topics.MAIN_TOPIC);
//        Assert.assertNotNull(recordFromPupControl, "wasn't found requestControl from PUP to PPOD \n");
//        System.out.println("requestControl\n");
//        searcher.printRecordFromKafka(recordFromPupControl);
//        // проверяем что сообщение ушло от PPOD_REQUEST  к PPOD_PUP с command =extract:control:response.(проверка завершена)
//        ConsumerRecord<String, String> recordToPupControl = searcher.findMessageByHeadersAndKey(key,
//                Map.of("message_type", "ACCEPT",
//                        "receiver_code",Topics.PPOD_PUP.toUpperCase(Locale.ROOT),
//                        "command", "extract:control:response"), Topics.MAIN_TOPIC);
//        Assert.assertNotNull(recordToPupControl, "wasn't found the request extract:control:response from PPOD_CONTROL to PPOD_PUP \n");
//        System.out.println("extract:control:response\n");
//        searcher.printRecordFromKafka(recordToPupControl);
//        /**
//         * отсюда начинается процесс записи в ЖУЗ
//         */
//        // проверяем что сообщение ушло в ppod для записи в ЖУЗ
//        ConsumerRecord<String, String> recordFromPupJournalRecord = searcher.findMessageByHeadersAndKey(key,Map.of("receiver_code",Topics.PPOD.toUpperCase(Locale.ROOT),
//                "command","putRequestJournalRecord"), Topics.MAIN_TOPIC);
//        Assert.assertNotNull(recordFromPupJournalRecord, "wasn't found putRequestJournalRecord from PUP to PPOD \n");
//        System.out.println("putRequestJournalRecord\n");
//        searcher.printRecordFromKafka(recordFromPupJournalRecord);
//        // проверяем что сообщение ушло от PPOD_REQUEST  к PPOD_PUP с command =extract:journal:input:accept.(запись в жуз добавлена ) TODO смотреть запись в базе
//        ConsumerRecord<String, String> recordToPupJournalRecord = searcher.findMessageByHeadersAndKeyWithCommit(key,
//                Map.of("message_type", "ACCEPT",
//                        "receiver_code",Topics.PPOD_PUP.toUpperCase(Locale.ROOT),
//                        "command", "extract:journal:input:accept"), Topics.MAIN_TOPIC);
//        Assert.assertNotNull(recordToPupJournalRecord, "wasn't found the request extract:journal:input:accept from PPOD_CONTROL to PPOD_PUP \n");
//        System.out.println("extract:journal:input:accept\n");
//        searcher.printRecordFromKafka(recordToPupJournalRecord);
//        /**
//         * отсюда начинается процесс проверки бесплатности предоставления сведений
//         */
//        // проверяем что сообщение ушло в ppod для проверки бесплатности
//        ConsumerRecord<String, String> recordFromPupDuty = searcher.findMessageByHeadersAndKey(key,Map.of("receiver_code",Topics.PPOD.toUpperCase(Locale.ROOT),
//                "command","calculateDuty"), Topics.MAIN_TOPIC);
//        Assert.assertNotNull(recordFromPupDuty, "wasn't found request command = calculateDuty from PUP to PPOD \n");
//        System.out.println("calculateDuty\n");
//        searcher.printRecordFromKafka(recordFromPupDuty);
//        // проверяем что сообщение ушло от PPOD_PAY  к PPOD_PUP (сведения пока предоставляются всегда бесплатно )
//        ConsumerRecord<String, String> recordToPupDuty = searcher.findMessageByHeadersAndKey(key,
//                Map.of("message_type", "ACCEPT",
//                        "receiver_code",Topics.PPOD_PUP.toUpperCase(Locale.ROOT),
//                        "command", "pay:calculate-duty:accept"), Topics.MAIN_TOPIC);
//        Assert.assertNotNull(recordToPupDuty, "wasn't found the request pay:calculate-duty:accept from PPOD_PAY to PPOD_PUP \n");
//        System.out.println("pay:calculate-duty:accept\n");
//        searcher.printRecordFromKafka(recordToPupDuty);
    }

    public String sendExtraction(String topicToSend) {
        FgislkKafkaProducerPpod producer = new FgislkKafkaProducerPpod(kafkaConfig); // send request in kafka
        String traceKey = UUID.randomUUID().toString();
        String key = traceKey;
        String kafka_correlationId = UUID.randomUUID().toString();
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("receiver_code", List.of("PPOD"));
        headers.put("message_type", List.of("REQUEST"));
        headers.put("sender_code", List.of("LKL"));
        headers.put("command", List.of("data:get"));
        headers.put("trace_key", List.of(traceKey));
        headers.put("kafka_correlationId", List.of(kafka_correlationId));
        headers.put("attachment_ref", List.of("request.xml=tax/1_2_10_11/UPLOADED_FILE"));


        String message = "{\"request\":{}}";
        String allureName = "Extraction request";
        producer.send(topicToSend, headers, key, message, allureName);
        System.out.println("kafka_correlationId :" + kafka_correlationId);
        System.out.println("key :" + key);

        return kafka_correlationId;

    }
}
