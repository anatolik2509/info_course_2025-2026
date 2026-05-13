package org.example.notesapp.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.example.notesapp.dto.NoteCacheDto;
import org.example.notesapp.model.Note;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Кэш заметок через Redis (Jedis).
 * В Redis хранится NoteCacheDto (без Hibernate-связей),
 * а наружу возвращаются Note-объекты.
 * При недоступности Redis приложение продолжает работать — все методы
 * перехватывают исключения и возвращают null / ничего не делают.
 */
@Slf4j
@Component
public class NoteCache {

    private static final int TTL_SECONDS = 300;
    private static final String KEY_PREFIX = "notes:user:";

    private final JedisPool jedisPool;
    private final ObjectMapper mapper;

    public NoteCache(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Получить список заметок пользователя из кэша.
     * @return список Note (без user-ссылки) или null при промахе / ошибке
     */
    public List<Note> getUserNotes(Long userId) {
        try (Jedis jedis = jedisPool.getResource()) {
            String json = jedis.get(KEY_PREFIX + userId);
            if (json == null) {
                log.debug("Redis MISS: {}{}", KEY_PREFIX, userId);
                return null;
            }
            log.debug("Redis HIT: {}{}", KEY_PREFIX, userId);
            List<NoteCacheDto> dtos = mapper.readValue(json, new TypeReference<>() {});
            return dtos.stream().map(NoteCacheDto::toEntity).toList();
        } catch (Exception e) {
            log.warn("Redis недоступен (get): {}", e.getMessage());
            return null;
        }
    }

    /**
     * Сохранить список заметок пользователя в кэш с TTL.
     * Note → NoteCacheDto → JSON (без Hibernate-связей).
     */
    public void setUserNotes(Long userId, List<Note> notes) {
        try (Jedis jedis = jedisPool.getResource()) {
            List<NoteCacheDto> dtos = notes.stream().map(NoteCacheDto::fromEntity).toList();
            String json = mapper.writeValueAsString(dtos);
            jedis.setex(KEY_PREFIX + userId, TTL_SECONDS, json);
            log.debug("Redis SET: {}{} (TTL={}с, {} заметок)", KEY_PREFIX, userId, TTL_SECONDS, notes.size());
        } catch (Exception e) {
            log.warn("Redis недоступен (set): {}", e.getMessage());
        }
    }

    /**
     * Удалить кэш списка заметок пользователя (инвалидация).
     */
    public void evictUserNotes(Long userId) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(KEY_PREFIX + userId);
            log.debug("Redis EVICT: {}{}", KEY_PREFIX, userId);
        } catch (Exception e) {
            log.warn("Redis недоступен (evict): {}", e.getMessage());
        }
    }
}
