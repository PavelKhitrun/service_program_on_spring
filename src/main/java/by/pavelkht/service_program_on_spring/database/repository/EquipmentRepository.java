package by.pavelkht.service_program_on_spring.database.repository;

import by.pavelkht.service_program_on_spring.database.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
