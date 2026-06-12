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
        UserEntity userEntity = new UserEntity("johndoe@email.com", "password2020", Role.USER);
        userEntity.setId(1L);

        //Act
        UserResponseDTO result = userMapper.toDTO(userEntity);

        //Assert
        assertEquals(1L, result.getId());
        assertEquals("johndoe@email.com", result.getEmail());
        assertEquals(Role.USER, result.getRole());
    }

    @Test
    void toEntity_shouldReturnUserEntity() {
        //Arrange
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setEmail("willem@gmail.com");
        requestDTO.setPassword("123123");
        requestDTO.setRole(Role.ADMIN);

        //Act
        UserEntity result = userMapper.toEntity(requestDTO);

        //Assert
        assertEquals("willem@gmail.com", result.getEmail());
        assertEquals("123123", result.getPassword());
        assertEquals(Role.ADMIN, result.getRole());
    }
}