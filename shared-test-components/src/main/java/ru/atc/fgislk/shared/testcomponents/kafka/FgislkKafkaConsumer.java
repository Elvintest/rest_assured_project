package ru.atc.fgislk.shared.testcomponents.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class FgislkKafkaConsumer {
    private static Logger LOGGER;
    private final KafkaConsumer<String, String> consumer;
    /**
     * топик, который будем слушать
     */
    private final String topicName;
    /**
     * время ожидания. Сеть не очень быстрая, милисекунды не работают, не успевает кафка подтвердить за 100мс чтение. Поэтому ставлю сюда значение от души, минуту
     */
    private Duration duration;

    /**
     * конструктор без возможности указать Duration. Поэтому время будет 1 минута
     *
     * @param stend
     * @param topic
     * @param consumerGroup
     */
    public FgislkKafkaConsumer(String stend, String topic, String consumerGroup) {
        this(stend, topic, consumerGroup, Duration.ofMinutes(1));
    }

    /**
     * конструктор с возможностью указать Duration
     *
     * @param stend
     * @param topic
     * @param consumerGroup
     * @param duration
     */
    public FgislkKafkaConsumer(String stend, String topic, String consumerGroup, Duration duration) {
        Properties properties = new Properties();
        LOGGER = LoggerFactory.getLogger(this.getClass());
        topicName = topic;
        this.duration = duration;

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, stend);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        properties.setProperty(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topicName));//, new AlwaysSeekToEndListener<String, String>(consumer));
    }

    public ConsumerRecords<String, String> poll() {

        ConsumerRecords<String, String> messages = consumer.poll(duration);
        if (messages.count() == 0) {
            LOGGER.info("В топике нет записей");
            return messages;
        }
        for(ConsumerRecord<String, String> record:messages) {
            LOGGER.info("partition:{}, offset:{}, время создания:{}, {} ",
                    record.partition(),
                    record.offset(),
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(record.timestamp()), ZoneId.systemDefault()),
                    record.value());
        }
        return messages;
    }

    public void commitAsync() {
        consumer.commitAsync();
    }

    public void close() {
        //kafkaConsumer.commitSync();
        consumer.close();
    }

    /**
     * Проверка получения ответа в топике
     *
     * @param clazz      Клас ответа
     * @param softAssert мягкая проверка
     * @return запись в топике или null
     */
    public <T> T searchMessageOnConsumer(String numberStatement, Class<T> clazz, SoftAssert softAssert) {
//        Gson gson = new Gson();
//        T recordTopic = null;
//
//        while (true) {
//            // Получение из топика
//            final ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(10));
//            // Проверим на количество скаченныйх сообщений
//            if (consumerRecords.count() != 0) {
//                for (ConsumerRecord<String, String> recordItem : consumerRecords) {
//                    recordTopic = gson.fromJson(recordItem.value(), clazz);
//
//                    // Если нашли нужный номер заявления
//                    if (recordTopic.getRequestNumber().equals(numberStatement)) {
//                        kafkaConsumer.close();
//                        LOGGER.info(gson.toJson(recordTopic));
//                        stringToAllureLog("Полученное сообщение", gson.toJson(recordTopic));
//                        return recordTopic;
//                    }
//                }
//            } else {
//                kafkaConsumer.close();
//                softAssert.assertNotNull(recordTopic, "Не пришел ответ в топик [-- " + nameConsumerTopic + " --]");
//                return null; // Выход из while запись не найдена
//            }
//        }
        return null;
    }

}

