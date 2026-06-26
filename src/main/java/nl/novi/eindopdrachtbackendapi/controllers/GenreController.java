package nl.novi.eindopdrachtbackendapi.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackendapi.dtos.genre.GenreRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.genre.GenreResponseDTO;
import nl.novi.eindopdrachtbackendapi.helpers.UrlHelper;
import nl.novi.eindopdrachtbackendapi.services.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;
    private final UrlHelper urlHelper;

    public GenreController(GenreService genreService, UrlHelper urlHelper) {
        this.genreService = genreService;
        this.urlHelper = urlHelper;
    }

    @PostMapping
    public ResponseEntity<GenreResponseDTO> createGenre(@Valid @RequestBody GenreRequestDTO genreRequestDTO) {
        GenreResponseDTO createdGenre = genreService.createGenre(genreRequestDTO);
        URI location = urlHelper.getCurrentUrlWithId(createdGenre.getId());
        return ResponseEntity.created(location).body(createdGenre);
    }

    @GetMapping
    public ResponseEntity<List<GenreResponseDTO>> getAllGenres() {
        List<GenreResponseDTO> getAllGenres = genreService.getAllGenres();
        return ResponseEntity.ok(getAllGenres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> getGenreById(@PathVariable Long id) {
        GenreResponseDTO getGenreById = genreService.getGenreById(id);
        return ResponseEntity.ok(getGenreById);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenreById(@PathVariable Long id) {
        genreService.deleteGenreById(id);
        return ResponseEntity.noContent().build();
    }
}
