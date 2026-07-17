package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.content.ContentResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.ContentEntity;
import nl.novi.eindopdrachtbackendapi.entities.FilmEntity;
import nl.novi.eindopdrachtbackendapi.entities.GenreEntity;
import nl.novi.eindopdrachtbackendapi.entities.SeriesEntity;
import nl.novi.eindopdrachtbackendapi.helpers.ContentDeletionHelper;
import nl.novi.eindopdrachtbackendapi.mappers.ContentMapper;
import nl.novi.eindopdrachtbackendapi.repositories.ContentRepository;
import nl.novi.eindopdrachtbackendapi.repositories.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private ContentMapper contentMapper;

    @InjectMocks
    private ContentService contentService;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private ContentDeletionHelper contentDeletionHelper;

    @Test
    void getAllContent_shouldReturnListOfContentResponseDTOs() {

        //Arrange
        ContentResponseDTO contentdto1 = new ContentResponseDTO();
        contentdto1.setId(10L);
        contentdto1.setTitle("The Matrix");
        contentdto1.setDescription("A computer hacker learns from " +
                "mysterious rebels about the true nature of his reality " +
                "and his role in the war against its controllers.");
        contentdto1.setAgeClassification(18);

        ContentResponseDTO contentdto2 = new ContentResponseDTO();
        contentdto2.setId(15L);
        contentdto2.setTitle("Breaking Bad");
        contentdto2.setDescription("A high school chemistry teacher " +
                "dying of cancer teams with a former student to secure " +
                "his family's future by manufacturing and selling " +
                "crystal meth.");
        contentdto2.setAgeClassification(16);

        FilmEntity contentEntity1 = new FilmEntity();
        contentEntity1.setId(10L);
        contentEntity1.setTitle("matrix"); // lowercase in database
        contentEntity1.setDescription("A computer hacker learns from " +
                "mysterious rebels about the true nature of his reality " +
                "and his role in the war against its controllers.");
        contentEntity1.setAgeClassification(18);

        SeriesEntity contentEntity2 = new SeriesEntity();
        contentEntity2.setId(15L);
        contentEntity2.setTitle("breaking bad"); // lowercase in database
        contentEntity2.setDescription("A high school chemistry teacher " +
                "dying of cancer teams with a former student to secure " +
                "his family's future by manufacturing and selling " +
                "crystal meth.");
        contentEntity2.setAgeClassification(16);

        when(contentRepository.findAll()).thenReturn(Arrays.asList(contentEntity1, contentEntity2));
        when(contentMapper.toDTO(contentEntity1)).thenReturn(contentdto1);
        when(contentMapper.toDTO(contentEntity2)).thenReturn(contentdto2);

        //Act
        List<ContentResponseDTO> result = contentService.getAllContent();

        //Assert
        assertEquals(2, result.size());
        assertEquals(contentdto1, result.get(0));
        assertEquals(contentdto2, result.get(1));
    }

    @Test
    void getContentById_shouldReturnContentResponseDTO() {

        //Arrange
        FilmEntity contentEntity1 = new FilmEntity();
        contentEntity1.setId(10L);
        contentEntity1.setTitle("matrix");

        ContentResponseDTO contentdto1 = new ContentResponseDTO();
        contentdto1.setId(10L);
        contentdto1.setTitle("The Matrix");

        when(contentRepository.findById(10L)).thenReturn(Optional.of(contentEntity1));
        when(contentMapper.toDTO(contentEntity1)).thenReturn(contentdto1);

        //Act
        ContentResponseDTO result = contentService.getContentById(10L);

        // Assert
        assertEquals(contentdto1, result);
    }

    @Test
    void getContentById_throwsException_whenContentNotFound() {
        //Arrange
        when(contentRepository.findById(555L)).thenReturn(Optional.empty());

        //Assert & Act
        assertThrows(RuntimeException.class, () -> contentService.getContentById(555L));
    }

    @Test
    void addGenresToContent_returnsUpdatedContentResponseDTO_whenContentExists() {
        //Arrange
        FilmEntity contentEntity = new FilmEntity();
        contentEntity.setId(10L);

        GenreEntity genre1 = new GenreEntity();
        genre1.setId(1L);
        genre1.setName("Action");

        GenreEntity genre2 = new GenreEntity();
        genre2.setId(2L);
        genre2.setName("Sci-Fi");

        List<Long> genreIds = List.of(1L, 2L);
        List<GenreEntity> genres = List.of(genre1, genre2);

        ContentResponseDTO contentResponseDTO = new ContentResponseDTO();
        contentResponseDTO.setId(10L);
        contentResponseDTO.setGenres(List.of("Action", "Sci-Fi"));

        when(contentRepository.findById(10L)).thenReturn(Optional.of(contentEntity));
        when(genreRepository.findAllById(genreIds)).thenReturn(genres);
        when(contentRepository.save(contentEntity)).thenReturn(contentEntity);
        when(contentMapper.toDTO(contentEntity)).thenReturn(contentResponseDTO);

        //Act
        ContentResponseDTO result = contentService.addGenresToContent(10L, genreIds);

        //Assert
        assertEquals(contentResponseDTO, result);
        assertEquals(genres, contentEntity.getGenres());
    }

    @Test
    void addGenresToContent_throwsException_whenContentNotFound() {
        //Arrange
        List<Long> genreIds = List.of(1L, 2L);
        when(contentRepository.findById(555L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(RuntimeException.class, () -> contentService.addGenresToContent(555L, genreIds));
    }

    @Test
    void deleteContentById_deletesContent_whenContentExists() {
        //Arrange
        when(contentRepository.existsById(10L)).thenReturn(true);

        doAnswer(invocation -> {
            Runnable action = invocation.getArgument(0);
            action.run();
            return null;
        }).when(contentDeletionHelper).deleteAndHandleIntegrity(any(Runnable.class));

        //Act
        contentService.deleteContentById(10L);

        //Assert
        verify(contentRepository).deleteById(10L);
        verify(contentRepository).flush();
    }

    @Test
    void deleteContentById_throwsException_whenContentNotFound() {
        //Arrange
        when(contentRepository.existsById(555L)).thenReturn(false);

        //Act & Assert
        assertThrows(RuntimeException.class, () -> contentService.deleteContentById(555L));
        verify(contentDeletionHelper, never()).deleteAndHandleIntegrity(any());
    }

    @Test
    void attachFile_returnsUpdatedContentResponseDTO_whenFileIsValid() throws IOException {
        //Arrange
        FilmEntity contentEntity = new FilmEntity();
        contentEntity.setId(10L);

        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn("image/png");
        when(file.getBytes()).thenReturn(new byte[]{1, 2, 3});
        when(file.getOriginalFilename()).thenReturn("poster.png");

        ContentResponseDTO contentResponseDTO = new ContentResponseDTO();
        contentResponseDTO.setId(10L);
        contentResponseDTO.setFileName("poster.png");
        contentResponseDTO.setContentType("image/png");
        contentResponseDTO.setHasFile(true);

        when(contentRepository.findById(10L)).thenReturn(Optional.of(contentEntity));
        when(contentRepository.save(contentEntity)).thenReturn(contentEntity);
        when(contentMapper.toDTO(contentEntity)).thenReturn(contentResponseDTO);

        //Act
        ContentResponseDTO result = contentService.attachFile(10L, file);

        //Assert
        assertEquals(contentResponseDTO, result);
        assertEquals("poster.png", contentEntity.getFileName());
        assertEquals("image/png", contentEntity.getContentType());
    }

    @Test
    void attachFile_throwsException_whenFileIsEmpty() {
        //Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> contentService.attachFile(10L, file));
    }

    @Test
    void attachFile_throwsException_whenContentTypeIsInvalid() {
        //Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn("text/plain");

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> contentService.attachFile(10L, file));
    }

    @Test
    void attachFile_throwsException_whenContentNotFound() {
        //Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn("image/png");
        when(contentRepository.findById(555L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(RuntimeException.class, () -> contentService.attachFile(555L, file));
    }

    @Test
    void getContentEntityById_returnsContentEntity_whenContentExists() {
        //Arrange
        FilmEntity contentEntity = new FilmEntity();
        contentEntity.setId(10L);

        when(contentRepository.findById(10L)).thenReturn(Optional.of(contentEntity));

        //Act
        ContentEntity result = contentService.getContentEntityById(10L);

        //Assert
        assertEquals(contentEntity, result);
    }

    @Test
    void getContentEntityById_throwsException_whenContentNotFound() {
        //Arrange
        when(contentRepository.findById(555L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(RuntimeException.class, () -> contentService.getContentEntityById(555L));
    }
}