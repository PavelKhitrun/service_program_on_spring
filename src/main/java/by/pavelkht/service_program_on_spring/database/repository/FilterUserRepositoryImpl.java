package by.pavelkht.service_program_on_spring.database.repository;

import by.pavelkht.service_program_on_spring.database.entity.User;
import by.pavelkht.service_program_on_spring.dto.UserFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FilterUserRepositoryImpl implements FilterUserRepository {

    @Override
    public Specification<User> byFilters(UserFilter filter){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.role() != null) {
                predicates.add(cb.like(root.get("role"), filter.role().toString()));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
