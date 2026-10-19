package by.pavelkht.service_program_on_spring.database.repository;

import by.pavelkht.service_program_on_spring.database.entity.User;
import by.pavelkht.service_program_on_spring.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>,
        FilterUserRepository, JpaSpecificationExecutor<User> {


    Boolean existsByLoginAndEmail(String login, String email);

    Optional<UserDto> findByLoginAndPassword(String login, String password);

    List<User> findAll();

    Page<User> findAll(Specification<User> spec, Pageable pageable);

    Optional<User> findByLogin(String login);

}
