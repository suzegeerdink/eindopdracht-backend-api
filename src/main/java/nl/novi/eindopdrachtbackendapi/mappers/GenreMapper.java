package nl.novi.eindopdrachtbackendapi.mappers;

import nl.novi.eindopdrachtbackendapi.dtos.genre.GenreRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.genre.GenreResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.GenreEntity;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {
    public static GenreResponseDTO toDTO(GenreEntity genre) {
        GenreResponseDTO dto = new GenreResponseDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }

    public static GenreEntity toEntity(GenreRequestDTO dto) {
        GenreEntity genre = new GenreEntity();
        genre.setName(dto.getName());
        return genre;
    }

}
