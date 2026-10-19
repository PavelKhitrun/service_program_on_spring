package by.pavelkht.service_program_on_spring.mapper;


import by.pavelkht.service_program_on_spring.database.entity.*;
import by.pavelkht.service_program_on_spring.dto.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserMapper implements Mapper<UserDto, User> {

    @Override
    public UserDto mapFrom(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .role(user.getRole())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .login(user.getLogin())
                .password(user.getPassword())
                .image(user.getImage())
                .build();
    }
}
