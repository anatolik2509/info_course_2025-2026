package org.example.notesapp.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Публикует события об изменении заметок в Kafka-топик.
 *
 * Ключ сообщения — имя пользователя: благодаря этому все события одного
 * пользователя ложатся в одну партицию и сохраняют порядок.
 * Значение — JSON {@code NoteEventDto}, сериализованный в строку.
 */
@Component
@Slf4j
public class NoteEventProducer {

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    @Value("${kafka.topic.note-events}")
    private String topic;

    public void publish(String username, String eventJson) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, username, eventJson);
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
                log.error("Не удалось опубликовать событие в Kafka: {}", exception.getMessage(), exception);
            } else {
                log.info("Событие отправлено в Kafka: topic={}, partition={}, offset={}, key={}",
                        metadata.topic(), metadata.partition(), metadata.offset(), username);
            }
        });
    }
}
