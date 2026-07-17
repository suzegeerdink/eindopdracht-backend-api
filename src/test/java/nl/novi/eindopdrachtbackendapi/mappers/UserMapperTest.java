package nl.novi.eindopdrachtbackendapi.mappers;

import nl.novi.eindopdrachtbackendapi.dtos.user.UserRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.user.UserResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.UserEntity;
import nl.novi.eindopdrachtbackendapi.enums.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    UserMapper userMapper = new UserMapper();

    @Test
    void toDTO_shouldReturnUserResponseDTO() {
        //Arrange
        UserEntity userEntity = new UserEntity("johndoe@email.com");
        userEntity.setId(1L);

        //Act
        UserResponseDTO result = userMapper.toDTO(userEntity);

        //Assert
        assertEquals(1L, result.getId());
        assertEquals("johndoe@email.com", result.getEmail());
    }

    @Test
    void toEntity_shouldReturnUserEntity() {
        //Arrange
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setEmail("willem@gmail.com");

        String keycloakId = "e20f1cff-b712-4248-bf77-656d48b33310";

        //Act
        UserEntity result = userMapper.toEntity(requestDTO, keycloakId);

        //Assert
        assertEquals("willem@gmail.com", result.getEmail());
        assertEquals(keycloakId, result.getKeycloakId());
    }
}