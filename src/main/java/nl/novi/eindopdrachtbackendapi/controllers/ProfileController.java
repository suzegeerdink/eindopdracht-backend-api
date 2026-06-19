package nl.novi.eindopdrachtbackendapi.controllers;

import nl.novi.eindopdrachtbackendapi.dtos.profile.ProfileRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.profile.ProfileResponseDTO;
import nl.novi.eindopdrachtbackendapi.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<ProfileResponseDTO> createProfile(@RequestBody ProfileRequestDTO profileRequestDTO) {
        ProfileResponseDTO createdProfile = profileService.createProfile(profileRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
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
    public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable Long id, @RequestBody ProfileRequestDTO profileRequestDTO) {
        ProfileResponseDTO updatedProfile = profileService.updateProfile(id, profileRequestDTO);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
