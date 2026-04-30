package org.example.notesapp.repository;

import org.example.notesapp.model.Note;
import org.example.notesapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserOrderByUpdatedAtDesc(User user);
    List<Note> findByUserIdOrderByUpdatedAtDesc(Long userId);
}
