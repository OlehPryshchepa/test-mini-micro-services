package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

@Component
@Slf4j
public class CustomKafkaListener {
    @KafkaListener(topics = "topic-1", groupId = "group1")
    void listenerTopic1(String data) {
        System.out.println("I this information from kafka producer. Information: " + data);
        log.info("Received message [{}] in group1", data);
    }

    @KafkaListener(topics = "topic-2", groupId = "group1")
    void listenerTopic2(String data) {
        log.info("Received message [{}] in group1", data);
    }

    @KafkaListener(topics = "topic-1,topic-2", groupId = "group1")
    void listener(@Payload String data,
                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                  @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("Received message [{}] from group1, partition-{} with offset-{}",
                data,
                partition,
                offset);
    }

    @KafkaListener(
            groupId = "group2",
            topicPartitions = @TopicPartition(topic = "topic-2",
                    partitionOffsets = {
                            @PartitionOffset(partition = "0", initialOffset = "0"),
                            @PartitionOffset(partition = "3", initialOffset = "0")}))
    public void listenToPartition(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Received Message [{}] from partition-{}",
                message,
                partition);
    }
}
