package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.content.ContentResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.FilmEntity;
import nl.novi.eindopdrachtbackendapi.entities.SeriesEntity;
import nl.novi.eindopdrachtbackendapi.mappers.ContentMapper;
import nl.novi.eindopdrachtbackendapi.repositories.ContentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private ContentMapper contentMapper;

    @InjectMocks
    private ContentService contentService;

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
}