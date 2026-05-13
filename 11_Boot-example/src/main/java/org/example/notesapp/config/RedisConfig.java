package org.example.notesapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Slf4j
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(8);
        poolConfig.setMaxIdle(4);

        log.info("Создаём JedisPool: {}:{}", host, port);
        return new JedisPool(poolConfig, host, port);
    }
}
