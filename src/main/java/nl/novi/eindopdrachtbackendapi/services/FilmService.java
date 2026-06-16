package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.film.FilmRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.film.FilmResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.FilmEntity;
import nl.novi.eindopdrachtbackendapi.mappers.FilmMapper;
import nl.novi.eindopdrachtbackendapi.repositories.FilmRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class FilmService {
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    public FilmService(FilmRepository filmRepository,  FilmMapper filmMapper) {
        this.filmRepository = filmRepository;
        this.filmMapper = filmMapper;
    }

    @Transactional(readOnly = true)
    public FilmResponseDTO getFilmById(Long id) {
        FilmEntity film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found"));
        return filmMapper.toDTO(film);
    }

    @Transactional(readOnly = true)
    public List<FilmResponseDTO> getAllFilms() {
        List<FilmEntity> films = filmRepository.findAll(Sort.by("id"));
        return films.stream()
                .map(filmMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FilmResponseDTO createFilm(FilmRequestDTO dto) {
        if (filmRepository.findByTitle(dto.getTitle()).isPresent()) {
            throw new RuntimeException("Film already exists");
        }

        FilmEntity film = filmMapper.toEntity(dto);
        FilmEntity createdFilm = filmRepository.save(film);
        return filmMapper.toDTO(createdFilm);
    }

    @Transactional
    public FilmResponseDTO updateFilm(Long id, FilmRequestDTO dto) {
        FilmEntity film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found"));

        film.setTitle(dto.getTitle());
        film.setDescription(dto.getDescription());
        film.setDuration(dto.getDuration());
        film.setAgeClassification(dto.getAgeClassification());

        FilmEntity filmUpdated = filmRepository.save(film);
        return filmMapper.toDTO(filmUpdated);
    }

    @Transactional
    public void deleteFilmById(Long id) {
        if (!filmRepository.existsById(id)) {
            throw new RuntimeException("Film not found");
        }
        filmRepository.deleteById(id);
    }
}
