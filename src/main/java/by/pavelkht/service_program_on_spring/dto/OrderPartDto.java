package by.pavelkht.service_program_on_spring.dto;

import java.math.BigDecimal;

public record OrderPartDto(Long id,
                           int article,
                           String partName,
                           int quantity,
                           BigDecimal price ) {
}
