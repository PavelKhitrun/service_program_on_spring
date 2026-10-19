package by.pavelkht.service_program_on_spring.integration.service;

import by.pavelkht.service_program_on_spring.ServiceProgramOnSpringApplication;
import by.pavelkht.service_program_on_spring.database.entity.Order;
import by.pavelkht.service_program_on_spring.database.entity.OrderStatus;
import by.pavelkht.service_program_on_spring.database.entity.User;
import by.pavelkht.service_program_on_spring.database.entity.UserRole;
import by.pavelkht.service_program_on_spring.database.repository.UserRepository;
import by.pavelkht.service_program_on_spring.dto.*;
import by.pavelkht.service_program_on_spring.mapper.CreateUserMapper;
import by.pavelkht.service_program_on_spring.mapper.UserMapper;
import by.pavelkht.service_program_on_spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ServiceProgramOnSpringApplication.class)
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserServiceIT {

    private final UserService userService;
    private final UserMapper userMapper;
    private final CreateUserMapper createUserMapper;

    @MockitoBean
    private final UserRepository userRepository;

    @Test
    void findByIdIT(){
        when(userRepository.findById(10L)).thenReturn(Optional.of(User.builder().name("testUser").build()));
        assertEquals(userService.findById(10L).get().getName(), "testUser");
    }

    @Test
    void getAllUsersIT() {
        when(userRepository.findAll()).thenReturn(List.of(User.builder().name("TestName").build()));

        var result = userService.findAll();
        assertEquals(UserDto.class, result.get(0).getClass(), "Возвращает класс UserDto");
        assertTrue(result.size() >= 1, "Ожидался список из более чем 1 элемента");
        assertTrue(result.get(0).getName().equals("TestName"));
    }

    @Test
    void createUserSuccessIT() {
        CreateUserDto createUserDto = CreateUserDto
                .builder()
                .organization("Test Organization")
                .email("testuser@email.ru")
                .phone("123-456")
                .username("testuser")
                .password("testpassword123")
                .confirmPassword("testpassword123")
                .build();

        when(userRepository.saveAndFlush(createUserMapper.mapFrom(createUserDto)))
                .thenReturn(User.builder().id(1L).name("testName").build());

        var result = userService.createUser(createUserDto);

        assertEquals(result.getId().equals(1L), true);
        assertEquals(result.getName().equals("testName"), true);
    }

    @Test
    void updateUserSuccessIT() {
        EditUserDto editUserDto = EditUserDto.builder().organization("newOrganization").image(new MockMultipartFile("test", new byte[0])).build();

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(User.builder().id(1L).name("oldOrganization").build()));

        when(userRepository.saveAndFlush(User.builder().build()))
                .thenReturn(User.builder().build());

        var result = userService.update(1L, editUserDto);
        assertEquals(result.get().getName(), "newOrganization");
    }

    @Test
    public void deleteUserSuccessIT() {
        when(userRepository.findById(10L))
                .thenReturn(Optional.of(User.builder().id(10L).build()));

        doNothing().when(userRepository).delete(User.builder().id(10L).build());

        assertEquals(userService.deleteUser(10L),  true);
    }

    @Test
    public void findByUsernameIT(){
        when(userRepository.findByLogin("testuser"))
                .thenReturn(Optional.of(User.builder().id(10L).login("testuser").name("testName").build()));

        assertEquals(userService.findByUsername("testuser").get().getName(), "testName");
    }
}
