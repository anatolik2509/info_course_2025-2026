package org.example.notesapp.kafka;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.example.notesapp.websocket.WebSocketSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

/**
 * Читает события из Kafka-топика и рассылает их во все локальные WebSocket-сессии
 * соответствующего пользователя.
 *
 * Зачем нужна эта прослойка между NoteService и WebSocket?
 *  - Приложение можно запустить в несколько инстансов за балансировщиком.
 *  - WebSocket-сессия пользователя живёт на одной конкретной инстанции.
 *  - При изменении заметки нужно уведомить эту сессию, какой бы инстанс ни обработал HTTP-запрос.
 *  - Kafka делает fan-out: каждая инстанция получает событие и пробует доставить
 *    его в свои локальные сессии (если пользователь не подключён — событие просто игнорируется).
 *
 * Поток-потребитель крутится в фоне; останавливается через {@link KafkaConsumer#wakeup()}.
 */
@Component
@Slf4j
public class NoteEventConsumer {

    @Autowired
    private Properties kafkaConsumerProperties;

    @Value("${kafka.topic.note-events}")
    private String topic;

    private KafkaConsumer<String, String> consumer;
    private Thread pollThread;

    @PostConstruct
    public void start() {
        consumer = new KafkaConsumer<>(kafkaConsumerProperties);
        consumer.subscribe(List.of(topic));

        pollThread = new Thread(this::pollLoop, "kafka-note-consumer");
        pollThread.setDaemon(true);
        pollThread.start();
        log.info("KafkaConsumer запущен, подписан на топик '{}'", topic);
    }

    private void pollLoop() {
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, String> record : records) {
                    String username = record.key();
                    String payload = record.value();
                    log.info("Получено из Kafka (partition={}, offset={}): user={}",
                            record.partition(), record.offset(), username);
                    if (username != null) {
                        WebSocketSessionManager.sendToUser(username, payload);
                    }
                }
            }
        } catch (WakeupException ignored) {
            // штатный сигнал остановки — выходим из цикла
        } catch (Exception e) {
            log.error("Ошибка в Kafka poll-цикле: {}", e.getMessage(), e);
        } finally {
            consumer.close();
            log.info("KafkaConsumer закрыт");
        }
    }

    @PreDestroy
    public void stop() {
        log.info("Останавливаем KafkaConsumer...");
        consumer.wakeup();
        try {
            pollThread.join(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
