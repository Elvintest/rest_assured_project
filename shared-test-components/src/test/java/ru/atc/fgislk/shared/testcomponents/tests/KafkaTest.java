package ru.atc.fgislk.shared.testcomponents.tests;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.header.Header;
import org.testng.annotations.Test;
import ru.atc.fgislk.shared.testcomponents.enums.KafkaEnum;
import ru.atc.fgislk.shared.testcomponents.kafka.FgislkKafkaConsumer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

public class KafkaTest {

    @Test
    public void kafkaConsumerTest() throws InterruptedException {
        FgislkKafkaConsumer kafka = new FgislkKafkaConsumer(KafkaEnum.DEV.getBootstrapServers(), "geo_at", "geo_consumers", Duration.ofSeconds(1));
        LocalDateTime stopTime = LocalDateTime.now().plusMinutes(1);
        while (LocalDateTime.now().isBefore(stopTime)) {
            ConsumerRecords<String, String> recs = kafka.poll();
            for(ConsumerRecord<String, String> rec:recs) {
                System.out.println(rec.value());
            }
        }
    }
}
