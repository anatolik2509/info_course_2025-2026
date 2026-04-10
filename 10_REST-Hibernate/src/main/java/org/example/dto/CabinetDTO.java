package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Cabinet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CabinetDTO {
    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private Integer booksCount;

    public static CabinetDTO fromEntity(Cabinet cabinet) {
        CabinetDTO dto = new CabinetDTO();
        dto.setId(cabinet.getId());
        dto.setName(cabinet.getName());
        dto.setLocation(cabinet.getLocation());
        dto.setCapacity(cabinet.getCapacity());
        dto.setBooksCount(cabinet.getBooks() != null ? cabinet.getBooks().size() : 0);
        return dto;
    }
}
