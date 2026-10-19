package by.pavelkht.service_program_on_spring.dto;

import by.pavelkht.service_program_on_spring.database.entity.OrderStatus;

import java.time.LocalDateTime;

public record OrderDto (Long id,
                        LocalDateTime orderDate,
                        OrderStatus status) {
}
