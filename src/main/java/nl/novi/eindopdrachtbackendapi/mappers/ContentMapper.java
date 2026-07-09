package nl.novi.eindopdrachtbackendapi.mappers;

import nl.novi.eindopdrachtbackendapi.dtos.content.ContentResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.ContentEntity;
import nl.novi.eindopdrachtbackendapi.entities.GenreEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ContentMapper {

    public ContentResponseDTO toDTO(ContentEntity content) {
        ContentResponseDTO dto = new ContentResponseDTO();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setDescription(content.getDescription());
        dto.setAgeClassification(content.getAgeClassification());
        dto.setGenres(content.getGenres().stream()
                .map(GenreEntity::getName)
                .collect(Collectors.toList()));

        dto.setFileName(content.getFileName());
        dto.setContentType(content.getContentType());
        dto.setHasFile(content.getFileData() != null);

        return dto;
    }
}
