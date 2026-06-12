package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.user.UserRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.user.UserResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.UserEntity;
import nl.novi.eindopdrachtbackendapi.enums.Role;
import nl.novi.eindopdrachtbackendapi.mappers.UserMapper;
import nl.novi.eindopdrachtbackendapi.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserById_returnsUserResponseDTO_whenUserExists() {
        //Arrange
        UserEntity userEntity = new UserEntity("test@email.com", "password", Role.USER);
        userEntity.setId(1L);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1L);
        userResponseDTO.setEmail("test@email");
        userResponseDTO.setRole(Role.USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDTO(userEntity)).thenReturn(userResponseDTO);

        //Act
        UserResponseDTO result = userService.getUserById(1L);

        //Assert
        assertEquals(userResponseDTO, result);
    }

    @Test
    void getUserById_throwsException_whenUserNotFound() {
        //Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
    }

    @Test
    void createUser_returnsUserResponseDTO_whenUserCreated() {
        //Arrange
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setEmail("new_user@email.com");
        requestDTO.setPassword("1234");
        requestDTO.setRole(Role.USER);

        UserEntity userEntity = new UserEntity("new_user@email.com", "1234", Role.USER);


        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail("new_user@email.com");
        userResponseDTO.setRole(Role.USER);

        when(userRepository.findByEmail("new_user@email.com")).thenReturn(Optional.empty());
        when(userMapper.toEntity(requestDTO)).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(userResponseDTO);

        //Act
        UserResponseDTO result = userService.createUser(requestDTO);

        //Assert
        assertEquals(userResponseDTO, result);
    }

    @Test
    void createUser_throwsException_whenEmailAlreadyExists() {
        //Arrange
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setEmail("existing@email.com");

        when(userRepository.findByEmail("existing@email.com")).thenReturn(Optional.of(new UserEntity()));

        //Act & Assert
        assertThrows(RuntimeException.class, () -> userService.createUser(requestDTO));
    }

    @Test
    void getAllUsers_returnsListOfUserResponseDTOs() {
        //Arrange
        UserEntity user1 = new UserEntity("user1@email.com", "1234",  Role.USER);
        UserEntity user2 = new UserEntity("user2@email.com", "5678",  Role.ADMIN);
        List<UserEntity> users = List.of(user1, user2);

        UserResponseDTO userResponseDTO1 = new UserResponseDTO();
        userResponseDTO1.setEmail("user1@email.com");
        userResponseDTO1.setRole(Role.USER);

        UserResponseDTO userResponseDTO2 = new UserResponseDTO();
        userResponseDTO2.setEmail("user2@email.com");
        userResponseDTO2.setRole(Role.ADMIN);

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDTO(user1)).thenReturn(userResponseDTO1);
        when(userMapper.toDTO(user2)).thenReturn(userResponseDTO2);

        //Act
        List<UserResponseDTO> result = userService.getAllUsers();

        //Assert
        assertEquals(2, result.size());
        assertEquals(userResponseDTO1, result.get(0));
        assertEquals(userResponseDTO2, result.get(1));
    }

}