
package nl.novi.eindopdrachtbackendapi.integration;

import nl.novi.eindopdrachtbackendapi.entities.FilmEntity;
import nl.novi.eindopdrachtbackendapi.entities.ProfileEntity;
import nl.novi.eindopdrachtbackendapi.entities.UserEntity;
import nl.novi.eindopdrachtbackendapi.repositories.FilmRepository;
import nl.novi.eindopdrachtbackendapi.repositories.ProfileRepository;
import nl.novi.eindopdrachtbackendapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest()
public class LoanIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private FilmRepository filmRepository;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    private Long youngProfileId;
    private Long adultFilmId;

    @BeforeEach
    void setUp() {
        UserEntity user = new UserEntity("integration-test@example.com");
        user.setKeycloakId("test-keycloak-id-loan");
        userRepository.save(user);

        // Profiel van 10 jaar oud
        ProfileEntity kidsProfile = new ProfileEntity(user, "kids-test", LocalDate.now().minusYears(10));
        profileRepository.save(kidsProfile);
        youngProfileId = kidsProfile.getId();

        // Film met leeftijdsclassificatie 18
        FilmEntity film = new FilmEntity("Test Film 18+", "Test description", 18, 120);
        filmRepository.save(film);
        adultFilmId = film.getId();
    }

    @Test
    void createLoan_returnsConflict_whenProfileTooYoungForContent() throws Exception {
        String requestBody = String.format(
                "{\"profileId\": %d, \"contentId\": %d, \"loanedOut\": true}",
                youngProfileId, adultFilmId
        );

        mockMvc.perform(post("/loans")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("age")));
    }
}
