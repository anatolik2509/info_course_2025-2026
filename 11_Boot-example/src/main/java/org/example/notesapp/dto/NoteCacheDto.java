package org.example.notesapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.notesapp.model.Note;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO для хранения заметки в Redis Hash.
 * Содержит только сериализуемые поля — без Hibernate-сущностей (User).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteCacheDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NoteCacheDto fromEntity(Note note) {
        return new NoteCacheDto(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }

    public Note toEntity() {
        Note note = new Note();
        note.setId(id);
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedAt(createdAt);
        note.setUpdatedAt(updatedAt);
        return note;
    }

    /** Конвертация в Map для Redis HSET */
    public Map<String, String> toMap() {
        return Map.of(
                "id", String.valueOf(id),
                "title", title != null ? title : "",
                "content", content != null ? content : "",
                "createdAt", createdAt != null ? createdAt.toString() : "",
                "updatedAt", updatedAt != null ? updatedAt.toString() : ""
        );
    }

    /** Конвертация из Map после Redis HGETALL */
    public static NoteCacheDto fromMap(Map<String, String> map) {
        return new NoteCacheDto(
                Long.parseLong(map.get("id")),
                map.get("title"),
                map.get("content"),
                parseDateTime(map.get("createdAt")),
                parseDateTime(map.get("updatedAt"))
        );
    }

    private static LocalDateTime parseDateTime(String value) {
        return (value != null && !value.isEmpty()) ? LocalDateTime.parse(value) : null;
    }
}
