package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.series.SeriesRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.series.SeriesResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.SeriesEntity;
import nl.novi.eindopdrachtbackendapi.mappers.SeriesMapper;
import nl.novi.eindopdrachtbackendapi.repositories.ContentRepository;
import nl.novi.eindopdrachtbackendapi.repositories.SeriesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService {
    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    @Transactional(readOnly = true)
    public SeriesResponseDTO getSeriesById(Long id) {
        SeriesEntity series = seriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Series not found"));
        return SeriesMapper.toDTO(series);
    }

    @Transactional(readOnly = true)
    public List<SeriesResponseDTO> getAllSeries() {
        List<SeriesEntity> series = seriesRepository.findAll();
        return series.stream()
                .map(SeriesMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SeriesResponseDTO createSeries(SeriesRequestDTO dto) {
        if (seriesRepository.findByTitle(dto.getTitle()).isPresent()) {
            throw new RuntimeException("Series already exists");
        }

        SeriesEntity series = SeriesMapper.toEntity(dto);
        SeriesEntity createdSeries = seriesRepository.save(series);
        return SeriesMapper.toDTO(createdSeries);
    }

    @Transactional
    public SeriesResponseDTO updateSeries(Long id, SeriesRequestDTO dto) {
        SeriesEntity series = seriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Series not found"));

        series.setTitle(dto.getTitle());
        series.setDescription(dto.getDescription());
        series.setAgeClassification(dto.getAgeClassification());
        series.setNumberOfEpisodes(dto.getNumberOfEpisodes());
        series.setNumberOfSeasons(dto.getNumberOfSeasons());

        SeriesEntity seriesUpdated = seriesRepository.save(series);
        return SeriesMapper.toDTO(seriesUpdated);
    }

    @Transactional
    public void deleteSeriesById(Long id) {
        if (!seriesRepository.existsById(id)) {
            throw new RuntimeException("Series not found");
        }
        seriesRepository.deleteById(id);
    }
}
