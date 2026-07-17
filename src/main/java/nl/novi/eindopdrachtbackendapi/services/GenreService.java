package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.genre.GenreRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.genre.GenreResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.GenreEntity;
import nl.novi.eindopdrachtbackendapi.exceptions.DuplicateResourceException;
import nl.novi.eindopdrachtbackendapi.exceptions.ResourceNotFoundException;
import nl.novi.eindopdrachtbackendapi.mappers.GenreMapper;
import nl.novi.eindopdrachtbackendapi.repositories.GenreRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreService(GenreRepository genreRepository,  GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    @Transactional(readOnly = true)
    public GenreResponseDTO getGenreById(Long id) {
        GenreEntity genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));
        return genreMapper.toDTO(genre);
    }

    @Transactional(readOnly = true)
    public List<GenreResponseDTO> getAllGenres() {
        List<GenreEntity> genres = genreRepository.findAll(Sort.by("id"));
        return genres.stream()
                .map(genreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public GenreResponseDTO createGenre(GenreRequestDTO dto) {
        if (genreRepository.findByName(dto.getName()).isPresent()) {
            throw new DuplicateResourceException("Genre with name " + dto.getName() + " already exists");
        }

        GenreEntity genre = genreMapper.toEntity(dto);
        GenreEntity newGenre = genreRepository.save(genre);
        return genreMapper.toDTO(newGenre);
    }

    @Transactional
    public void deleteGenreById(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new ResourceNotFoundException("Genre not found");
        }
        try {
            genreRepository.deleteById(id);
            genreRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete genre: genre is still linked to one or more content items");
        }
    }
}
