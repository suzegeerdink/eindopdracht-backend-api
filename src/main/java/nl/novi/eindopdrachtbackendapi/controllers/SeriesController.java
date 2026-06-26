package nl.novi.eindopdrachtbackendapi.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackendapi.dtos.series.SeriesRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.series.SeriesResponseDTO;
import nl.novi.eindopdrachtbackendapi.helpers.UrlHelper;
import nl.novi.eindopdrachtbackendapi.services.SeriesService;
import nl.novi.eindopdrachtbackendapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController {

    private final SeriesService seriesService;
    private final UrlHelper urlHelper;

    public SeriesController(SeriesService seriesService, UrlHelper urlHelper) {
        this.seriesService = seriesService;
        this.urlHelper = urlHelper;
    }

    @PostMapping
    public ResponseEntity<SeriesResponseDTO> createSeries(@Valid @RequestBody SeriesRequestDTO seriesRequestDTO) {
        SeriesResponseDTO createdSeries = seriesService.createSeries(seriesRequestDTO);
        URI location = urlHelper.getCurrentUrlWithId(createdSeries.getId());
        return ResponseEntity.created(location).body(createdSeries);
    }

    @GetMapping
    public ResponseEntity<List<SeriesResponseDTO>> getAllSeries() {
        List<SeriesResponseDTO> getAllSeries = seriesService.getAllSeries();
        return ResponseEntity.status(HttpStatus.OK).body(getAllSeries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeriesResponseDTO> getSeriesById(@PathVariable Long id) {
        SeriesResponseDTO getSeriesById = seriesService.getSeriesById(id);
        return ResponseEntity.ok(getSeriesById);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeriesResponseDTO> updateSeries(@PathVariable Long id, @Valid @RequestBody SeriesRequestDTO seriesRequestDTO) {
        SeriesResponseDTO updatedSeries = seriesService.updateSeries(id, seriesRequestDTO);
        return ResponseEntity.ok(updatedSeries);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeriesById(@PathVariable Long id) {
        seriesService.deleteSeriesById(id);
        return ResponseEntity.noContent().build();
    }
}
