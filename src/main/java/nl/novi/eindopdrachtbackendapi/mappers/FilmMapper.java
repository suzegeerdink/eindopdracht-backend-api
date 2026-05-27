package nl.novi.eindopdrachtbackendapi.mappers;

import nl.novi.eindopdrachtbackendapi.dtos.film.FilmRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.film.FilmResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.FilmEntity;
import org.springframework.stereotype.Component;

@Component
public class FilmMapper {

    public static FilmResponseDTO toDTO(FilmEntity film) {
        FilmResponseDTO dto = new FilmResponseDTO();
        dto.setId(film.getId());
        dto.setTitle(film.getTitle());
        dto.setDescription(film.getDescription());
        dto.setAgeClassification(film.getAgeClassification());
        dto.setDuration(film.getDuration());
        return dto;
    }

    public static FilmEntity toEntity(FilmRequestDTO dto) {
        FilmEntity film = new FilmEntity();
        film.setTitle(dto.getTitle());
        film.setDescription(dto.getDescription());
        film.setAgeClassification(dto.getAgeClassification());
        film.setDuration(dto.getDuration());
        return film;
    }
}
