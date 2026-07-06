package nl.novi.eindopdrachtbackendapi.services;


import nl.novi.eindopdrachtbackendapi.dtos.profile.ProfileRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.profile.ProfileResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.ProfileEntity;
import nl.novi.eindopdrachtbackendapi.entities.ProfileSettingsEntity;
import nl.novi.eindopdrachtbackendapi.entities.UserEntity;
import nl.novi.eindopdrachtbackendapi.exceptions.ResourceNotFoundException;
import nl.novi.eindopdrachtbackendapi.mappers.ProfileMapper;
import nl.novi.eindopdrachtbackendapi.repositories.ProfileRepository;
import nl.novi.eindopdrachtbackendapi.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository,  UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public ProfileResponseDTO getProfileById(Long id) {
        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
        return ProfileMapper.toDTO(profile);
    }

    @Transactional(readOnly = true)
    public List<ProfileResponseDTO> getAllProfiles() {
        List<ProfileEntity> profiles = profileRepository.findAll();
        return profiles.stream()
                .map(ProfileMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProfileResponseDTO createProfile(ProfileRequestDTO dto) {
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        ProfileEntity profile = ProfileMapper.toEntity(dto, user);

        ProfileSettingsEntity settings = new ProfileSettingsEntity(
                true,
                "nl",
                profile
        );

        profile.setSettings(settings);

        ProfileEntity createdProfile = profileRepository.save(profile);
        return ProfileMapper.toDTO(createdProfile);
    }

    @Transactional
    public ProfileResponseDTO updateProfile(Long id, ProfileRequestDTO dto) {
        ProfileEntity profile =  profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        profile.setBirthDate(dto.getBirthDate());
        profile.setDisplayName(dto.getDisplayName());

        ProfileEntity updatedProfile = profileRepository.save(profile);
        return ProfileMapper.toDTO(updatedProfile);
    }

    @Transactional
    public void deleteProfile(Long id) {
        if (!profileRepository.existsById(id)) {
            throw new ResourceNotFoundException("Profile not found");
        }
        profileRepository.deleteById(id);
    }

    public int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public boolean canAccessAdultContent(Long profileId, int ageClassification) {
        ProfileEntity profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        int age = calculateAge(profile.getBirthDate());
        return age >= ageClassification;
    }
}
