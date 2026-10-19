package by.pavelkht.service_program_on_spring.database.repository;

import by.pavelkht.service_program_on_spring.database.entity.OrderPart;
import by.pavelkht.service_program_on_spring.dto.OrderPartDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderPartRepository extends JpaRepository<OrderPart, Long> {


    List<OrderPart> findAllByorder_id(Long orederId);
}
