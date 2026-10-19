package by.pavelkht.service_program_on_spring.database.repository;

import by.pavelkht.service_program_on_spring.database.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {
}
