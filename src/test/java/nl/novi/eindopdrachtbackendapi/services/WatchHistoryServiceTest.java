package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.watchhisory.WatchHistoryRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.watchhisory.WatchHistoryResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.FilmEntity;
import nl.novi.eindopdrachtbackendapi.entities.ProfileEntity;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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

    @Test
    void getAllWatchHistories_shouldReturnListOfWatchHistoryResponseDTOs() {
        //Arrange
        WatchHistoryResponseDTO watchHistoryDto1 = new WatchHistoryResponseDTO();
        watchHistoryDto1.setId(100L);

        WatchHistoryResponseDTO watchHistoryDto2 = new WatchHistoryResponseDTO();
        watchHistoryDto2.setId(101L);

        WatchHistoryResponseDTO watchHistoryDto3 = new WatchHistoryResponseDTO();
        watchHistoryDto3.setId(102L);


        WatchHistoryEntity watchHistoryEntity1 = new WatchHistoryEntity();
        watchHistoryEntity1.setId(100L);

        WatchHistoryEntity watchHistoryEntity2 = new WatchHistoryEntity();
        watchHistoryEntity2.setId(101L);

        WatchHistoryEntity watchHistoryEntity3 = new WatchHistoryEntity();
        watchHistoryEntity3.setId(102L);

        when(watchHistoryRepository.findAll()).thenReturn(Arrays.asList(watchHistoryEntity1, watchHistoryEntity2, watchHistoryEntity3));
        when(watchHistoryMapper.toDTO(watchHistoryEntity1)).thenReturn(watchHistoryDto1);
        when(watchHistoryMapper.toDTO(watchHistoryEntity2)).thenReturn(watchHistoryDto2);
        when(watchHistoryMapper.toDTO(watchHistoryEntity3)).thenReturn(watchHistoryDto3);

        //Act
        List<WatchHistoryResponseDTO> result = watchHistoryService.getAllWatchHistories();

        //Assert
        assertEquals(3, result.size());
        assertEquals(watchHistoryDto1, result.get(0));
        assertEquals(watchHistoryDto2, result.get(1));
        assertEquals(watchHistoryDto3, result.get(2));
    }
}