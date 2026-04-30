package org.example.notesapp.controller;

import jakarta.validation.Valid;
import org.example.notesapp.dto.NoteDto;
import org.example.notesapp.model.Note;
import org.example.notesapp.model.User;
import org.example.notesapp.service.NoteService;
import org.example.notesapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listNotes(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        List<Note> notes = noteService.findAllByUser(user);
        model.addAttribute("notes", notes);
        model.addAttribute("username", user.getUsername());
        return "notes";
    }

    @GetMapping("/new")
    public String showNewNoteForm(Model model) {
        model.addAttribute("noteDto", new NoteDto());
        return "note-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditNoteForm(@PathVariable Long id, Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        Optional<Note> noteOpt = noteService.findById(id);

        if (noteOpt.isPresent()) {
            Note note = noteOpt.get();
            if (!note.getUser().getId().equals(user.getId())) {
                return "redirect:/notes?error=unauthorized";
            }

            NoteDto noteDto = new NoteDto();
            noteDto.setId(note.getId());
            noteDto.setTitle(note.getTitle());
            noteDto.setContent(note.getContent());

            model.addAttribute("noteDto", noteDto);
            return "note-form";
        }

        return "redirect:/notes?error=notfound";
    }

    @PostMapping("/save")
    public String saveNote(@Valid @ModelAttribute NoteDto noteDto,
                          BindingResult bindingResult,
                          Authentication authentication,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "note-form";
        }

        User user = userService.findByUsername(authentication.getName());

        if (noteDto.getId() != null) {
            noteService.updateNote(noteDto.getId(), noteDto.getTitle(), noteDto.getContent(), user);
        } else {
            Note note = new Note();
            note.setTitle(noteDto.getTitle());
            note.setContent(noteDto.getContent());
            note.setUser(user);
            noteService.createNote(note);
        }

        return "redirect:/notes";
    }

    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        try {
            noteService.deleteNote(id, user);
        } catch (RuntimeException e) {
            return "redirect:/notes?error=unauthorized";
        }
        return "redirect:/notes";
    }
}
