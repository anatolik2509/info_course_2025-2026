package org.example.notesapp.service;

import org.example.notesapp.model.Note;
import org.example.notesapp.model.User;
import org.example.notesapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> findAllByUser(User user) {
        return noteRepository.findByUserOrderByUpdatedAtDesc(user);
    }

    public Note createNote(Note note) {
        return noteRepository.save(note);
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
            return noteRepository.save(note);
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
        } else {
            throw new RuntimeException("Note not found");
        }
    }

    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }
}
