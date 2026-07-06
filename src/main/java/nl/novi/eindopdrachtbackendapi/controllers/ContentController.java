package nl.novi.eindopdrachtbackendapi.controllers;

import nl.novi.eindopdrachtbackendapi.dtos.content.ContentResponseDTO;
import nl.novi.eindopdrachtbackendapi.services.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public ResponseEntity<List<ContentResponseDTO>> getAllContents() {
        List<ContentResponseDTO> getAllContent = contentService.getAllContent();
        return ResponseEntity.ok(getAllContent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentResponseDTO> getContentById(@PathVariable Long id) {
        ContentResponseDTO contentResponseDTO = contentService.getContentById(id);
        return ResponseEntity.ok(contentResponseDTO);
    }

    @PutMapping("/{id}/genres")
    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    public ResponseEntity<ContentResponseDTO> addGenresToContent(@PathVariable Long id, @RequestBody List<Long> genreIds) {
        ContentResponseDTO updatedContent = contentService.addGenresToContent(id, genreIds);
        return ResponseEntity.ok(updatedContent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    public ResponseEntity<Void> deleteContentById(@PathVariable Long id) {
        contentService.deleteContentById(id);
        return ResponseEntity.noContent().build();
    }
}
