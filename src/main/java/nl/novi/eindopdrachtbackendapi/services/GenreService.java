package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.genre.GenreRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.genre.GenreResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.GenreEntity;
import nl.novi.eindopdrachtbackendapi.mappers.GenreMapper;
import nl.novi.eindopdrachtbackendapi.repositories.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public GenreResponseDTO getById(Long id) {
        GenreEntity genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        return GenreMapper.toDTO(genre);
    }

    @Transactional(readOnly = true)
    public List<GenreResponseDTO> getAllGenres() {
        List<GenreEntity> genres = genreRepository.findAll();
        return genres.stream()
                .map(GenreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public GenreResponseDTO addGenre(GenreRequestDTO dto) {
        if (genreRepository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Genre with name " + dto.getName() + " already exists");
        }

        GenreEntity genre = GenreMapper.toEntity(dto);
        GenreEntity newGenre = genreRepository.save(genre);
        return GenreMapper.toDTO(newGenre);
    }

    @Transactional
    public void deleteGenre(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new RuntimeException("genre not found");
        }
        genreRepository.deleteById(id);
    }
}
