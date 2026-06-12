package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.content.ContentResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.ContentEntity;
import nl.novi.eindopdrachtbackendapi.mappers.ContentMapper;
import nl.novi.eindopdrachtbackendapi.repositories.ContentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentService {
    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;

    public ContentService(ContentRepository contentRepository, ContentMapper contentMapper) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
    }

    @Transactional(readOnly = true)
    public ContentResponseDTO getContentById(Long id) {
        ContentEntity content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found"));
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
    public void deleteContentById(Long id) {
        if (!contentRepository.existsById(id)) {
            throw new RuntimeException("Content not found");
        }
        contentRepository.deleteById(id);
    }
}
