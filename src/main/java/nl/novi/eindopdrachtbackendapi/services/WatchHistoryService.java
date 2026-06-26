package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.watchhisory.WatchHistoryRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.watchhisory.WatchHistoryResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.ContentEntity;
import nl.novi.eindopdrachtbackendapi.entities.ProfileEntity;
import nl.novi.eindopdrachtbackendapi.entities.WatchHistoryEntity;
import nl.novi.eindopdrachtbackendapi.exceptions.ResourceNotFoundException;
import nl.novi.eindopdrachtbackendapi.mappers.WatchHistoryMapper;
import nl.novi.eindopdrachtbackendapi.repositories.ContentRepository;
import nl.novi.eindopdrachtbackendapi.repositories.ProfileRepository;
import nl.novi.eindopdrachtbackendapi.repositories.WatchHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchHistoryService {
    private final WatchHistoryRepository watchHistoryRepository;
    private final ProfileRepository profileRepository;
    private final ContentRepository contentRepository;
    private final WatchHistoryMapper watchHistoryMapper;

    public WatchHistoryService(WatchHistoryRepository watchHistoryRepository,
                               ProfileRepository profileRepository,
                               ContentRepository contentRepository,
                               WatchHistoryMapper watchHistoryMapper) {
        this.watchHistoryRepository = watchHistoryRepository;
        this.profileRepository = profileRepository;
        this.contentRepository = contentRepository;
        this.watchHistoryMapper = watchHistoryMapper;

    }

    @Transactional(readOnly = true)
    public WatchHistoryResponseDTO getWatchHistoryById(Long id) {
        WatchHistoryEntity watchHistory = watchHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WatchHistory not found"));
        return watchHistoryMapper.toDTO(watchHistory);
    }

    @Transactional(readOnly = true)
    public List<WatchHistoryResponseDTO> getAllWatchHistories() {
        List<WatchHistoryEntity> watchHistories = watchHistoryRepository.findAll();
        return watchHistories.stream()
                .map(watchHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public WatchHistoryResponseDTO createWatchHistory(WatchHistoryRequestDTO dto) {
        ProfileEntity profile = profileRepository.findById(dto.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        ContentEntity content = contentRepository.findById(dto.getContentId())
                .orElseThrow(() -> new ResourceNotFoundException("Content not found"));

        WatchHistoryEntity watchHistory = watchHistoryMapper.toEntity(dto, content, profile);
        watchHistory.setWatchDate(LocalDate.now());

        WatchHistoryEntity createdWatchHistory = watchHistoryRepository.save(watchHistory);
        return watchHistoryMapper.toDTO(createdWatchHistory);
    }

    @Transactional
    public WatchHistoryResponseDTO updateWatchHistory(Long id, WatchHistoryRequestDTO dto) {
        WatchHistoryEntity watchHistory = watchHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WatchHistory not found"));

        ProfileEntity profile = profileRepository.findById(dto.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        ContentEntity content = contentRepository.findById(dto.getContentId())
                .orElseThrow(() -> new ResourceNotFoundException("Content not found"));

        watchHistory.setContent(content);
        watchHistory.setProfile(profile);

        WatchHistoryEntity updatedWatchHistory = watchHistoryRepository.save(watchHistory);
        return watchHistoryMapper.toDTO(updatedWatchHistory);
    }

    @Transactional
    public void deleteWatchHistory(Long id) {
        if (!watchHistoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("WatchHistory not found");
        }
        watchHistoryRepository.deleteById(id);
    }
}
