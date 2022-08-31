package ru.atc.fgislk.ppod.testcore.utilsPpod;

import io.qameta.allure.Step;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static ru.atc.fgislk.shared.testcomponents.Util.mapHeadersToLogString;
import static ru.atc.fgislk.shared.testcomponents.Util.stringToAllureLog;

public class FgislkKafkaProducerPpod {
    private static final Logger LOGGER = LoggerFactory.getLogger(FgislkKafkaProducerPpod.class);

    private final KafkaProducer<String, String> kafka;

    RecordHeader headers;

    public FgislkKafkaProducerPpod(String stend) {
        Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, stend);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.setProperty(ProducerConfig.ACKS_CONFIG, "1");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");

        kafka = new KafkaProducer<>(properties);
    }

    /**
     * Отправить сообщение в топик Kafka
     * @param topic топик отправки сообщения
     * @param headers карта заголовков со значениями
     * @param key ключ
     * @param message сообщение
     * @param allureName название для лога
     */
    @Step("Отправить сообщение в кафку")
    public void send(String topic, Map<String, List<String>> headers, String key, String message, String allureName) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            for (String value : entry.getValue())
                record.headers().add(entry.getKey(), value.getBytes(StandardCharsets.UTF_8));
        }
        kafka.send(record);
        flush();
//        LOGGER.info("Топик {}; ключ {}; сообщение: {}; заголовки: {}", topic,key,message, mapHeadersToLogString(headers));
        stringToAllureLog(allureName, message);
//        if (!headers.isEmpty())
//            stringToAllureLog("заголовки сообщения", mapHeadersToLogString(headers));
    }

    public void close() {
        kafka.flush();
        kafka.close();
    }

    public void flush() {
        kafka.flush();
    }


}
