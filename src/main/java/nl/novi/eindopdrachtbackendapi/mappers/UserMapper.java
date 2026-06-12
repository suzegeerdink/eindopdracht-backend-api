package nl.novi.eindopdrachtbackendapi.mappers;

import nl.novi.eindopdrachtbackendapi.dtos.user.UserRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.user.UserResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toDTO(UserEntity user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    public UserEntity toEntity(UserRequestDTO dto) {
        UserEntity user = new UserEntity();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }
}
