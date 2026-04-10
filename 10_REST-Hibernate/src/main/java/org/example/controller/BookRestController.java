package org.example.controller;

import org.example.dto.BookDTO;
import org.example.dto.CreateBookRequest;
import org.example.entity.Book;
import org.example.entity.Cabinet;
import org.example.service.BookService;
import org.example.service.CabinetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;
    private final CabinetService cabinetService;

    @Autowired
    public BookRestController(BookService bookService, CabinetService cabinetService) {
        this.bookService = bookService;
        this.cabinetService = cabinetService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.findAll().stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(book -> ResponseEntity.ok(BookDTO.fromEntity(book)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam String query) {
        List<BookDTO> books = bookService.searchByTitle(query).stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search-criteria")
    public ResponseEntity<List<BookDTO>> searchBooksCriteria(@RequestParam String query) {
        List<BookDTO> books = bookService.searchByTitleCriteria(query).stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody CreateBookRequest request) {
        Cabinet cabinet = cabinetService.findById(request.getCabinetId())
                .orElseThrow(() -> new RuntimeException("Cabinet not found with id: " + request.getCabinetId()));

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setCabinet(cabinet);

        Book savedBook = bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(BookDTO.fromEntity(savedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
