package nl.novi.eindopdrachtbackendapi.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackendapi.dtos.watchhisory.WatchHistoryRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.watchhisory.WatchHistoryResponseDTO;
import nl.novi.eindopdrachtbackendapi.helpers.UrlHelper;
import nl.novi.eindopdrachtbackendapi.services.WatchHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/watch-history")
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;
    private final UrlHelper urlHelper;

    public WatchHistoryController(WatchHistoryService watchHistoryService, UrlHelper urlHelper) {
        this.watchHistoryService = watchHistoryService;
        this.urlHelper = urlHelper;
    }

    @PostMapping
    public ResponseEntity<WatchHistoryResponseDTO> createWatchHistory(@Valid @RequestBody WatchHistoryRequestDTO watchHistoryRequestDTO) {
        WatchHistoryResponseDTO createdWatchHistory = watchHistoryService.createWatchHistory(watchHistoryRequestDTO);
        URI location = urlHelper.getCurrentUrlWithId(createdWatchHistory.getId());
        return ResponseEntity.created(location).body(createdWatchHistory);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<WatchHistoryResponseDTO>> getAllWatchHistory() {
        List<WatchHistoryResponseDTO> getAllWatchHistories = watchHistoryService.getAllWatchHistories();
        return ResponseEntity.ok().body(getAllWatchHistories);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WatchHistoryResponseDTO> getWatchHistoryById(@PathVariable Long id) {
        WatchHistoryResponseDTO getWatchHistoryById = watchHistoryService.getWatchHistoryById(id);
        return ResponseEntity.ok().body(getWatchHistoryById);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WatchHistoryResponseDTO> updateWatchHistory(@PathVariable Long id, @Valid @RequestBody WatchHistoryRequestDTO watchHistoryRequestDTO) {
        WatchHistoryResponseDTO updatedWatchHistory = watchHistoryService.updateWatchHistory(id, watchHistoryRequestDTO);
        return ResponseEntity.ok().body(updatedWatchHistory);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteWatchHistory(@PathVariable Long id) {
        watchHistoryService.deleteWatchHistory(id);
        return ResponseEntity.noContent().build();
    }
}
