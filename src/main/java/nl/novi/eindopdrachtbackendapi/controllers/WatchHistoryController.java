package nl.novi.eindopdrachtbackendapi.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackendapi.dtos.watchhisory.WatchHistoryRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.watchhisory.WatchHistoryResponseDTO;
import nl.novi.eindopdrachtbackendapi.services.WatchHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watch-history")
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;

    public WatchHistoryController(WatchHistoryService watchHistoryService) {
        this.watchHistoryService = watchHistoryService;
    }

    @PostMapping
    public ResponseEntity<WatchHistoryResponseDTO> createWatchHistory(@Valid @RequestBody WatchHistoryRequestDTO watchHistoryRequestDTO) {
        WatchHistoryResponseDTO createdWatchHistory = watchHistoryService.createWatchHistory(watchHistoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWatchHistory);
    }

    @GetMapping
    public ResponseEntity<List<WatchHistoryResponseDTO>> getAllWatchHistory() {
        List<WatchHistoryResponseDTO> getAllWatchHistories = watchHistoryService.getAllWatchHistories();
        return ResponseEntity.ok().body(getAllWatchHistories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WatchHistoryResponseDTO> getWatchHistoryById(@PathVariable Long id) {
        WatchHistoryResponseDTO getWatchHistoryById = watchHistoryService.getWatchHistoryById(id);
        return ResponseEntity.ok().body(getWatchHistoryById);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WatchHistoryResponseDTO> updateWatchHistory(@PathVariable Long id, @Valid @RequestBody WatchHistoryRequestDTO watchHistoryRequestDTO) {
        WatchHistoryResponseDTO updatedWatchHistory = watchHistoryService.updateWatchHistory(id, watchHistoryRequestDTO);
        return ResponseEntity.ok().body(updatedWatchHistory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchHistory(@PathVariable Long id) {
        watchHistoryService.deleteWatchHistory(id);
        return ResponseEntity.noContent().build();
    }
}
