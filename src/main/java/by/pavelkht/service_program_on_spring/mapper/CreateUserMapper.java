package by.pavelkht.service_program_on_spring.mapper;


import by.pavelkht.service_program_on_spring.database.entity.*;
import by.pavelkht.service_program_on_spring.dto.*;
import org.springframework.stereotype.Component;


@Component
public class CreateUserMapper implements Mapper<User, CreateUserDto>{

    @Override
    public User mapFrom(CreateUserDto createUserDto) {
        return User
                .builder()
                .name(createUserDto.getOrganization())
                .email(createUserDto.getEmail())
                .phone(createUserDto.getPhone())
                .login(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .role(UserRole.USER)
                .build();
    }
}
