package nl.novi.eindopdrachtbackendapi.integration;

import nl.novi.eindopdrachtbackendapi.entities.FilmEntity;
import nl.novi.eindopdrachtbackendapi.repositories.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest()
public class ContentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmRepository filmRepository;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    private Long filmId;

    @BeforeEach
    void setUp() {
        FilmEntity film = new FilmEntity("Upload Test Film", "Test description", 12, 100);
        filmRepository.save(film);
        filmId = film.getId();
    }

    @Test
    void uploadContentFile_returnsBadRequest_whenFileTypeNotAllowed() throws Exception {
        MockMultipartFile invalidFile = new MockMultipartFile(
                "file", "notes.txt", "text/plain", "some text content".getBytes()
        );

        mockMvc.perform(multipart("/content/{id}/file", filmId)
                        .file(invalidFile)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_CONTENT_MANAGER"))))
                .andExpect(status().isBadRequest());
    }
}
