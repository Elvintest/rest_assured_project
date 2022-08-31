package ru.atc.fgislk.shared.testcomponents.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.Set;

public class AlwaysSeekToEndListener<K, V> implements ConsumerRebalanceListener {

    private Consumer<K, V> consumer;

    public AlwaysSeekToEndListener(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        Set<TopicPartition> tp = consumer.assignment();
        consumer.seekToEnd(partitions);
    }
}
