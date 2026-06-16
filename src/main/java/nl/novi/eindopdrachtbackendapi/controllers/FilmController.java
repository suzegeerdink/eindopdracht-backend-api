package nl.novi.eindopdrachtbackendapi.controllers;

import nl.novi.eindopdrachtbackendapi.dtos.film.FilmRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.film.FilmResponseDTO;
import nl.novi.eindopdrachtbackendapi.services.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<FilmResponseDTO> createFilm(@RequestBody FilmRequestDTO filmRequestDTO) {
        FilmResponseDTO createdFilm = filmService.createFilm(filmRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilm);
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
    public ResponseEntity<FilmResponseDTO> updateFilm(@PathVariable Long id, @RequestBody FilmRequestDTO filmRequestDTO) {
        FilmResponseDTO updatedFilm = filmService.updateFilm(id, filmRequestDTO);
        return ResponseEntity.ok(updatedFilm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilmById(@PathVariable Long id) {
        filmService.deleteFilmById(id);
        return ResponseEntity.noContent().build();
    }
}
