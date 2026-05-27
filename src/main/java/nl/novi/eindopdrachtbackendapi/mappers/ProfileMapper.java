package nl.novi.eindopdrachtbackendapi.mappers;

import nl.novi.eindopdrachtbackendapi.dtos.profile.ProfileRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.profile.ProfileResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.ProfileEntity;
import nl.novi.eindopdrachtbackendapi.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public static ProfileResponseDTO toDTO(ProfileEntity profile) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(profile.getId());
        dto.setUser_id(profile.getUser().getId());
        dto.setDisplayName(profile.getDisplayName());
        dto.setBirthDate(profile.getBirthDate());
        return dto;
    }

    public static ProfileEntity toEntity(ProfileRequestDTO dto, UserEntity user) {
        ProfileEntity profile = new ProfileEntity();
        profile.setDisplayName(dto.getDisplayName());
        profile.setBirthDate(dto.getBirthDate());
        profile.setUser(user);
        return profile;
    }
}
