package ru.atc.fgislk.ppod.testcore;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.header.Header;
import ru.atc.fgislk.ppod.testcore.constants.Topics;
import ru.atc.fgislk.shared.testcomponents.enums.StendsDescriptionEnum;
import ru.atc.fgislk.ppod.testcore.utilsPpod.FgislkKafkaConsumerPpod;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

public class Searcher {
    String kafkaConfig = StendsDescriptionEnum.DEV.getKafka().getBootstrapServers();
    String subscriptionGroup = "autoTestServer";

    public static void main(String[] args) {
        Searcher searcher = new Searcher();
//        Map<String, String> neededHeaders = new HashMap<>();
//        neededHeaders.put("kafka_correlationId", "e8396964-4920-4e47-8f43-02f5f1c7e7ed");
//        neededHeaders.put("receiver_code", "PUP");
        searcher.startTestAndCleanMainTopic();
    }

    public void findMessagesByHeaders(Map<String, String> headersForSearch, String topic) {
        FgislkKafkaConsumerPpod consumer = new FgislkKafkaConsumerPpod(kafkaConfig, topic,
                subscriptionGroup, Duration.ofMillis(100)); // kafka consumer
        LocalDateTime stopTime = LocalDateTime.now().plusMinutes(5);
        while (LocalDateTime.now().isBefore(stopTime)) {
            for (ConsumerRecord<String, String> record : consumer.poll()) {
                if (checkHeaders(record, headersForSearch)) {
                    printRecordFromKafka(record);
                }
            }
        }
        consumer.close();
    }

    public ConsumerRecord<String, String> findMessageByHeaders(Map<String, String> headersForSearch, String topic) {
        FgislkKafkaConsumerPpod consumer = new FgislkKafkaConsumerPpod(kafkaConfig, topic,
                subscriptionGroup, Duration.ofSeconds(1)); // kafka consumer
        LocalDateTime stopTime = LocalDateTime.now().plusMinutes(10);
        while (LocalDateTime.now().isBefore(stopTime)) {
            for (ConsumerRecord<String, String> record : consumer.poll()) {
                if (checkHeaders(record, headersForSearch)) {
                    consumer.close();
                    return record;
                }
            }
        }
        consumer.close();

        return null;
    }

    public ConsumerRecord<String, String> findByKey(String key, String topic) {
        FgislkKafkaConsumerPpod consumer = new FgislkKafkaConsumerPpod(kafkaConfig, topic,
                subscriptionGroup, Duration.ofSeconds(1)); // kafka consumer
        LocalDateTime stopTime = LocalDateTime.now().plusMinutes(5);
        while (LocalDateTime.now().isBefore(stopTime)) {
            for (ConsumerRecord<String, String> record : consumer.poll()) {
                if (record.key() != null && record.key().equals(key)) {
                    consumer.close();
                    consumer.commitAsync();
                    return record;
                }
            }
        }
        consumer.close();

        return null;
    }

    public void printRecordFromKafka(ConsumerRecord<String, String> record) {
        try {
            System.out.println("message : " + record.value());
            if (record.key() != null)
                System.out.println("key : " + record.key());
            System.out.println("HEADERS:");
            for (Header header1 : record.headers()) {
                System.out.println(header1.key() + ":" + new String(header1.value()));
            }
            System.out.println("\n");
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
        }

    }


    private boolean checkHeaders(ConsumerRecord<String, String> record, Map<String, String> headersForSearch) {
        int notMatchHeaders = headersForSearch.size();
        for (Header header : record.headers()) {
            for (Map.Entry<String, String> entry : headersForSearch.entrySet()) {
                if (header.key().equals(entry.getKey()) && new String(header.value()).equals(entry.getValue())) {
                    notMatchHeaders--;
                }
            }
        }

        return notMatchHeaders == 0;
    }

    public ConsumerRecord<String, String> findMessageByHeadersAndKey(String key, Map<String, String> headersForSearch, String topic) {
        FgislkKafkaConsumerPpod consumer = new FgislkKafkaConsumerPpod(kafkaConfig, topic,
                subscriptionGroup, Duration.ofSeconds(1)); // kafka consumer
        LocalDateTime stopTime = LocalDateTime.now().plusMinutes(5);
        while (LocalDateTime.now().isBefore(stopTime)) {
            for (ConsumerRecord<String, String> record : consumer.poll()) {
                if (record.key() != null && record.key().equals(key) && checkHeaders(record, headersForSearch)) {
                    consumer.close();
                    return record;
                }
            }
        }
        consumer.close();

        return null;
    }

    public ConsumerRecord<String, String> findMessageByHeadersAndKeyWithCommit(String key, Map<String, String> headersForSearch, String topic) {
        FgislkKafkaConsumerPpod consumer = new FgislkKafkaConsumerPpod(kafkaConfig, topic,
                subscriptionGroup, Duration.ofSeconds(1)); // kafka consumer
        LocalDateTime stopTime = LocalDateTime.now().plusMinutes(5);
        while (LocalDateTime.now().isBefore(stopTime)) {
            for (ConsumerRecord<String, String> record : consumer.poll()) {
                if (record.key() != null && record.key().equals(key) && checkHeaders(record, headersForSearch)) {
                    consumer.commitSync();
                    consumer.close();
                    return record;
                }
            }
        }
        consumer.close();

        return null;
    }

    public void startTestAndCleanMainTopic() {
        FgislkKafkaConsumerPpod consumer = new FgislkKafkaConsumerPpod(kafkaConfig, Topics.MAIN_TOPIC,
                subscriptionGroup, Duration.ofSeconds(1)); // kafka consumer
        LocalDateTime stopTime = LocalDateTime.now().plusMinutes(5);
        while (LocalDateTime.now().isBefore(stopTime)) {
            ConsumerRecords<String, String> messages = consumer.poll();
        }
        consumer.commitSync();
        consumer.close();
    }
}

