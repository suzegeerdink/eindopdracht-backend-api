package nl.novi.eindopdrachtbackendapi.controllers;

import nl.novi.eindopdrachtbackendapi.dtos.series.SeriesRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.series.SeriesResponseDTO;
import nl.novi.eindopdrachtbackendapi.services.SeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @PostMapping
    public ResponseEntity<SeriesResponseDTO> createSeries(@RequestBody SeriesRequestDTO seriesRequestDTO) {
        SeriesResponseDTO createdSeries = seriesService.createSeries(seriesRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSeries);
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
    public ResponseEntity<SeriesResponseDTO> updateSeries(@PathVariable Long id, @RequestBody SeriesRequestDTO seriesRequestDTO) {
        SeriesResponseDTO updatedSeries = seriesService.updateSeries(id, seriesRequestDTO);
        return ResponseEntity.ok(updatedSeries);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeriesById(@PathVariable Long id) {
        seriesService.deleteSeriesById(id);
        return ResponseEntity.noContent().build();
    }
}
