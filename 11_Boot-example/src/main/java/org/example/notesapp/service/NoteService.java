package org.example.notesapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.notesapp.cache.NoteCache;
import org.example.notesapp.dto.NoteEventDto;
import org.example.notesapp.model.Note;
import org.example.notesapp.model.User;
import org.example.notesapp.repository.NoteRepository;
import org.example.notesapp.websocket.WebSocketSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteCache noteCache;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Note> findAllByUser(User user) {
        List<Note> cached = noteCache.getUserNotes(user.getId());
        if (cached != null) {
            return cached;
        }
        List<Note> notes = noteRepository.findByUserOrderByUpdatedAtDesc(user);
        noteCache.setUserNotes(user.getId(), notes);
        return notes;
    }

    public Note createNote(Note note) {
        Note saved = noteRepository.save(note);
        noteCache.evictUserNotes(note.getUser().getId());
        sendEvent("CREATED", saved);
        return saved;
    }

    public Note updateNote(Long id, String title, String content, User user) {
        Optional<Note> noteOpt = noteRepository.findById(id);
        if (noteOpt.isPresent()) {
            Note note = noteOpt.get();
            if (!note.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Unauthorized");
            }
            note.setTitle(title);
            note.setContent(content);
            Note saved = noteRepository.save(note);
            noteCache.evictUserNotes(user.getId());
            sendEvent("UPDATED", saved);
            return saved;
        }
        throw new RuntimeException("Note not found");
    }

    public void deleteNote(Long id, User user) {
        Optional<Note> noteOpt = noteRepository.findById(id);
        if (noteOpt.isPresent()) {
            Note note = noteOpt.get();
            if (!note.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Unauthorized");
            }
            noteRepository.delete(note);
            noteCache.evictUserNotes(user.getId());
            sendEvent("DELETED", note);
        } else {
            throw new RuntimeException("Note not found");
        }
    }

    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }

    private void sendEvent(String type, Note note) {
        try {
            NoteEventDto event = new NoteEventDto(
                    type,
                    note.getId(),
                    note.getTitle(),
                    note.getContent(),
                    note.getUpdatedAt() != null ? note.getUpdatedAt().toString() : ""
            );
            String json = objectMapper.writeValueAsString(event);
            WebSocketSessionManager.sendToUser(note.getUser().getUsername(), json);
        } catch (Exception e) {
            log.error("Ошибка отправки WebSocket-события: {}", e.getMessage());
        }
    }
}
