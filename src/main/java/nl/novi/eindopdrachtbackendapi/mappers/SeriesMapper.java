package nl.novi.eindopdrachtbackendapi.mappers;

import nl.novi.eindopdrachtbackendapi.dtos.series.SeriesRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.series.SeriesResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.SeriesEntity;
import org.springframework.stereotype.Component;

@Component
public class SeriesMapper {

    public SeriesResponseDTO toDTO(SeriesEntity series) {
        SeriesResponseDTO dto = new SeriesResponseDTO();
        dto.setId(series.getId());
        dto.setTitle(series.getTitle());
        dto.setDescription(series.getDescription());
        dto.setAgeClassification(series.getAgeClassification());
        dto.setNumberOfSeasons(series.getNumberOfSeasons());
        dto.setNumberOfEpisodes(series.getNumberOfEpisodes());
        return dto;
    }

    public SeriesEntity toEntity(SeriesRequestDTO dto) {
        SeriesEntity series = new SeriesEntity();
        series.setTitle(dto.getTitle());
        series.setDescription(dto.getDescription());
        series.setAgeClassification(dto.getAgeClassification());
        series.setNumberOfSeasons(dto.getNumberOfSeasons());
        series.setNumberOfEpisodes(dto.getNumberOfEpisodes());
        return series;
    }
}
