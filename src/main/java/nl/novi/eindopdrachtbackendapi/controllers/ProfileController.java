package nl.novi.eindopdrachtbackendapi.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackendapi.dtos.profile.ProfileRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.profile.ProfileResponseDTO;
import nl.novi.eindopdrachtbackendapi.helpers.UrlHelper;
import nl.novi.eindopdrachtbackendapi.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final UrlHelper urlHelper;

    public ProfileController(ProfileService profileService, UrlHelper urlHelper) {
        this.profileService = profileService;
        this.urlHelper = urlHelper;
    }

    @PostMapping
    public ResponseEntity<ProfileResponseDTO> createProfile(@Valid @RequestBody ProfileRequestDTO profileRequestDTO) {
        ProfileResponseDTO createdProfile = profileService.createProfile(profileRequestDTO);
        URI location = urlHelper.getCurrentUrlWithId(createdProfile.getId());
        return ResponseEntity.created(location).body(createdProfile);
    }

    @GetMapping
    public ResponseEntity<List<ProfileResponseDTO>> getAllProfiles() {
        List<ProfileResponseDTO> getAllProfiles = profileService.getAllProfiles();
        return ResponseEntity.ok(getAllProfiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getProfileById(@PathVariable Long id) {
        ProfileResponseDTO getProfileById = profileService.getProfileById(id);
        return ResponseEntity.ok(getProfileById);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable Long id, @Valid @RequestBody ProfileRequestDTO profileRequestDTO) {
        ProfileResponseDTO updatedProfile = profileService.updateProfile(id, profileRequestDTO);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
