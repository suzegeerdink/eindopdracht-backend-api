package nl.novi.eindopdrachtbackendapi.mappers;

import nl.novi.eindopdrachtbackendapi.dtos.content.ContentResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.ContentEntity;
import org.springframework.stereotype.Component;

@Component
public class ContentMapper {

    public static ContentResponseDTO toDTO(ContentEntity content) {
        ContentResponseDTO dto = new ContentResponseDTO();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setDescription(content.getDescription());
        dto.setAgeClassification(content.getAgeClassification());
        return dto;
    }
}
