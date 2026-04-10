package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Book;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Long cabinetId;
    private String cabinetName;

    public static BookDTO fromEntity(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        if (book.getCabinet() != null) {
            dto.setCabinetId(book.getCabinet().getId());
            dto.setCabinetName(book.getCabinet().getName());
        }
        return dto;
    }
}
