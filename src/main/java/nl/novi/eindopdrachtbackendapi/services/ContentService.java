package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.content.ContentResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.ContentEntity;
import nl.novi.eindopdrachtbackendapi.entities.GenreEntity;
import nl.novi.eindopdrachtbackendapi.exceptions.ResourceNotFoundException;
import nl.novi.eindopdrachtbackendapi.mappers.ContentMapper;
import nl.novi.eindopdrachtbackendapi.repositories.ContentRepository;
import nl.novi.eindopdrachtbackendapi.repositories.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentService {
    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;
    private final GenreRepository genreRepository;

    public ContentService(ContentRepository contentRepository, ContentMapper contentMapper,  GenreRepository genreRepository) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public ContentResponseDTO getContentById(Long id) {
        ContentEntity content = contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found"));
        return contentMapper.toDTO(content);
    }

    @Transactional(readOnly = true)
    public List<ContentResponseDTO> getAllContent() {
        List<ContentEntity> content = contentRepository.findAll();
        return content.stream()
                .map(contentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ContentResponseDTO addGenresToContent(Long id, List<Long> genreIds) {
        ContentEntity content = contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found"));

        List<GenreEntity> genres = genreRepository.findAllById(genreIds);
        content.setGenres(genres);

        ContentEntity updatedContent = contentRepository.save(content);
        return contentMapper.toDTO(updatedContent);
    }


    @Transactional
    public void deleteContentById(Long id) {
        if (!contentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Content not found");
        }
        contentRepository.deleteById(id);
    }
}
