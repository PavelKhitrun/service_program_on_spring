package by.pavelkht.service_program_on_spring.database.repository;

import by.pavelkht.service_program_on_spring.database.entity.User;
import by.pavelkht.service_program_on_spring.dto.UserFilter;
import org.springframework.data.jpa.domain.Specification;

public interface FilterUserRepository {

    Specification<User> byFilters(UserFilter filter);
}
