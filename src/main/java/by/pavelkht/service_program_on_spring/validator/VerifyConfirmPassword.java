package by.pavelkht.service_program_on_spring.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class VerifyConfirmPassword implements ConstraintValidator <CheckConfirmPassword, PasswordConfirmable> {
    @Override
    public boolean isValid(PasswordConfirmable value, ConstraintValidatorContext context) {
        return value.password().equals(value.confirmPassword());
    }
}
