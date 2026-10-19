package by.pavelkht.service_program_on_spring.database.repository;

import by.pavelkht.service_program_on_spring.database.entity.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface WarrantyRepository extends JpaRepository<Warranty, Long> {
    Optional<Warranty> findWarrantyByOrder_Id(Long orderId);
}
