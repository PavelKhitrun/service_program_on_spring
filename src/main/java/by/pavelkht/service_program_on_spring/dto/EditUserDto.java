package by.pavelkht.service_program_on_spring.dto;


import by.pavelkht.service_program_on_spring.validator.CheckConfirmPassword;
import by.pavelkht.service_program_on_spring.validator.PasswordConfirmable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;


@Value
@Builder
@CheckConfirmPassword(message = "Пароль и подтверждение пароля не совпадают!")
public class EditUserDto implements PasswordConfirmable {


    @NotBlank(message = "Организация обязательна для заполнения")
    String organization;

    @NotBlank(message = "Логин не может быть пустым!")
    String username;

    @NotBlank
    @Size(min=3, max=15, message = "Длина телефона от 3 до 15 символов")
    String phone;

    @NotBlank
    @Email
    String email;

    @NotBlank(message = "Пароль не может быть пустым!")
    @Size(min=6, max=20, message = "Длина пароля от 6 до 20 символов")
    String password;

    @NotBlank(message = "Не указано подтверждение пароля")
    @Size(min=6, max=20, message = "Длина подтверждения пароля от 6 до 20 символов")
    String confirmPassword;

    MultipartFile image;

    @Override
    public String password() {
        return password;
    }

    @Override
    public String confirmPassword() {
        return confirmPassword;
    }


}
