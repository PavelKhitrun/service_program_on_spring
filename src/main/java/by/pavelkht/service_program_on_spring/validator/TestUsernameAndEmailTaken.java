package by.pavelkht.service_program_on_spring.validator;

import by.pavelkht.service_program_on_spring.database.repository.UserRepository;
import by.pavelkht.service_program_on_spring.dto.CreateUserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestUsernameAndEmailTaken implements ConstraintValidator <CheckUsernameAndEmailTaken, CreateUserDto> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(CreateUserDto value, ConstraintValidatorContext context) {
        return !userRepository.existsByLoginAndEmail(value.getUsername(), value.getEmail());
    }
}
