package org.example.notesapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteEventDto {
    private String type; // CREATED, UPDATED, DELETED
    private Long id;
    private String title;
    private String content;
    private String updatedAt;
}