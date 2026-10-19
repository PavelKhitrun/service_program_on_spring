package by.pavelkht.service_program_on_spring.dto;


import by.pavelkht.service_program_on_spring.database.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;


@Value
@Builder
@AllArgsConstructor
public class UserDto {
    Long id;
    String name;
    UserRole role;
    String email;
    String phone;
    String login;
    String password;
    String image;
}
