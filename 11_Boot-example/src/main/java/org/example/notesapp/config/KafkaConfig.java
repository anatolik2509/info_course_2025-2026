package org.example.notesapp.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.UUID;

/**
 * Конфигурация Kafka — чистые kafka-clients, без spring-kafka и без auto-configuration.
 *
 * Производитель и параметры потребителя создаются как обычные Spring-бины
 * (по аналогии с JedisPool в RedisConfig).
 */
@Configuration
@Slf4j
public class KafkaConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Один общий KafkaProducer на всё приложение — он потокобезопасен
     * и должен переиспользоваться. Spring вызовет close() при остановке контекста.
     */
    @Bean(destroyMethod = "close")
    public KafkaProducer<String, String> kafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // acks=all — ждём подтверждения от всех реплик (для одного брокера это просто запись на диск)
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "notes-app-producer");

        log.info("Создаём KafkaProducer для {}", bootstrapServers);
        return new KafkaProducer<>(props);
    }

    /**
     * Свойства для KafkaConsumer.
     *
     * ВАЖНО: group.id уникален для каждой инстанции приложения.
     * Это нужно, чтобы каждая инстанция получала ВСЕ сообщения топика
     * (а не делила их между собой, как было бы в одной группе).
     *
     * Зачем так? WebSocket-соединение пользователя живёт на конкретной инстанции,
     * и мы заранее не знаем — на какой. Поэтому событие "note CREATED" должно
     * прилететь во все инстансы; каждая из них переправит его в свои локальные сессии.
     */
    @Bean
    public Properties kafkaConsumerProperties() {
        String groupId = "notes-app-" + UUID.randomUUID();
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // latest — пропускаем накопившиеся за время простоя сообщения,
        // для уведомлений старые события неактуальны
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

        log.info("Параметры KafkaConsumer: bootstrap={}, group.id={}", bootstrapServers, groupId);
        return props;
    }
}
