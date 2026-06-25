package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.series.SeriesRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.series.SeriesResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.SeriesEntity;
import nl.novi.eindopdrachtbackendapi.exceptions.DuplicateResourceException;
import nl.novi.eindopdrachtbackendapi.exceptions.ResourceNotFoundException;
import nl.novi.eindopdrachtbackendapi.mappers.SeriesMapper;
import nl.novi.eindopdrachtbackendapi.repositories.ContentRepository;
import nl.novi.eindopdrachtbackendapi.repositories.SeriesRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService {
    private final SeriesRepository seriesRepository;
    private final SeriesMapper seriesMapper;

    public SeriesService(SeriesRepository seriesRepository,  SeriesMapper seriesMapper) {
        this.seriesRepository = seriesRepository;
        this.seriesMapper = seriesMapper;
    }

    @Transactional(readOnly = true)
    public SeriesResponseDTO getSeriesById(Long id) {
        SeriesEntity series = seriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Series not found"));
        return seriesMapper.toDTO(series);
    }

    @Transactional(readOnly = true)
    public List<SeriesResponseDTO> getAllSeries() {
        List<SeriesEntity> series = seriesRepository.findAll(Sort.by("id"));
        return series.stream()
                .map(seriesMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SeriesResponseDTO createSeries(SeriesRequestDTO dto) {
        if (seriesRepository.findByTitle(dto.getTitle()).isPresent()) {
            throw new DuplicateResourceException("Series already exists");
        }

        SeriesEntity series = seriesMapper.toEntity(dto);
        SeriesEntity createdSeries = seriesRepository.save(series);
        return seriesMapper.toDTO(createdSeries);
    }

    @Transactional
    public SeriesResponseDTO updateSeries(Long id, SeriesRequestDTO dto) {
        SeriesEntity series = seriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Series not found"));

        series.setTitle(dto.getTitle());
        series.setDescription(dto.getDescription());
        series.setAgeClassification(dto.getAgeClassification());
        series.setNumberOfEpisodes(dto.getNumberOfEpisodes());
        series.setNumberOfSeasons(dto.getNumberOfSeasons());

        SeriesEntity seriesUpdated = seriesRepository.save(series);
        return seriesMapper.toDTO(seriesUpdated);
    }

    @Transactional
    public void deleteSeriesById(Long id) {
        if (!seriesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Series not found");
        }
        seriesRepository.deleteById(id);
    }
}
