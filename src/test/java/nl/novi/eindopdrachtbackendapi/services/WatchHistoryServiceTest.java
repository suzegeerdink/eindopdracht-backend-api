package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.watchhisory.WatchHistoryRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.watchhisory.WatchHistoryResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.FilmEntity;
import nl.novi.eindopdrachtbackendapi.entities.ProfileEntity;
import nl.novi.eindopdrachtbackendapi.entities.UserEntity;
import nl.novi.eindopdrachtbackendapi.entities.WatchHistoryEntity;
import nl.novi.eindopdrachtbackendapi.mappers.WatchHistoryMapper;
import nl.novi.eindopdrachtbackendapi.repositories.ContentRepository;
import nl.novi.eindopdrachtbackendapi.repositories.ProfileRepository;
import nl.novi.eindopdrachtbackendapi.repositories.WatchHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.WatchService;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WatchHistoryServiceTest {

    @Mock
    private WatchHistoryRepository watchHistoryRepository;

    @Mock
    private WatchHistoryMapper watchHistoryMapper;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ContentRepository contentRepository;

    @InjectMocks
    private WatchHistoryService watchHistoryService;

    @Test
    void createWatchHistory_returnsWatchHistoryResponseDTO_whenWatchHistoryCreated() {
        //Arrange
        WatchHistoryRequestDTO watchHistoryRequestDTO = new WatchHistoryRequestDTO();
        watchHistoryRequestDTO.setContentId(2L);
        watchHistoryRequestDTO.setProfileId(30L);

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId(30L);

        FilmEntity contentEntity = new FilmEntity();
        contentEntity.setId(2L);

        WatchHistoryEntity watchHistoryEntity = new WatchHistoryEntity(profileEntity, contentEntity, LocalDate.now());

        WatchHistoryResponseDTO watchHistoryResponseDTO = new WatchHistoryResponseDTO();
        watchHistoryResponseDTO.setId(99L);

        when(profileRepository.findById(30L)).thenReturn(Optional.of(profileEntity));
        when(contentRepository.findById(2L)).thenReturn(Optional.of(contentEntity));
        when(watchHistoryMapper.toEntity(watchHistoryRequestDTO, contentEntity, profileEntity)).thenReturn(watchHistoryEntity);
        when(watchHistoryRepository.save(any(WatchHistoryEntity.class))).thenReturn(watchHistoryEntity);
        when(watchHistoryMapper.toDTO(watchHistoryEntity)).thenReturn(watchHistoryResponseDTO);

        //Act
        WatchHistoryResponseDTO result = watchHistoryService.createWatchHistory(watchHistoryRequestDTO);

        //Assert
        assertEquals(99L, result.getId());
    }
}