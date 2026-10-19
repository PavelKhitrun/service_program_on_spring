package by.pavelkht.service_program_on_spring.dto;

import by.pavelkht.service_program_on_spring.database.entity.UserRole;

public record UserFilter(
        UserRole role,
        String sort,
        String direction
) {
}
