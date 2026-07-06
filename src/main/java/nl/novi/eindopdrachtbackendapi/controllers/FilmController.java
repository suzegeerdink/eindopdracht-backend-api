package nl.novi.eindopdrachtbackendapi.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackendapi.dtos.film.FilmRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.film.FilmResponseDTO;
import nl.novi.eindopdrachtbackendapi.helpers.UrlHelper;
import nl.novi.eindopdrachtbackendapi.services.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;
    private final UrlHelper urlHelper;

    public FilmController(FilmService filmService,  UrlHelper urlHelper) {
        this.filmService = filmService;
        this.urlHelper = urlHelper;
    }

    @PostMapping
    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    public ResponseEntity<FilmResponseDTO> createFilm(@Valid @RequestBody FilmRequestDTO filmRequestDTO) {
        FilmResponseDTO createdFilm = filmService.createFilm(filmRequestDTO);
        URI location = urlHelper.getCurrentUrlWithId(createdFilm.getId());
        return ResponseEntity.created(location).body(createdFilm);
    }

    @GetMapping
    public ResponseEntity<List<FilmResponseDTO>> getAllFilms() {
        List<FilmResponseDTO> getAllFilms = filmService.getAllFilms();
        return ResponseEntity.ok(getAllFilms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmResponseDTO> getFilmById(@PathVariable Long id) {
        FilmResponseDTO getFilmById = filmService.getFilmById(id);
        return ResponseEntity.ok(getFilmById);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    public ResponseEntity<FilmResponseDTO> updateFilm(@PathVariable Long id, @Valid @RequestBody FilmRequestDTO filmRequestDTO) {
        FilmResponseDTO updatedFilm = filmService.updateFilm(id, filmRequestDTO);
        return ResponseEntity.ok(updatedFilm);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    public ResponseEntity<Void> deleteFilmById(@PathVariable Long id) {
        filmService.deleteFilmById(id);
        return ResponseEntity.noContent().build();
    }
}
