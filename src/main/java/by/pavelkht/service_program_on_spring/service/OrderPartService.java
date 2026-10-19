package by.pavelkht.service_program_on_spring.service;


import by.pavelkht.service_program_on_spring.database.repository.OrderPartRepository;
import by.pavelkht.service_program_on_spring.dto.OrderPartDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderPartService {
    private final OrderPartRepository orderPartRepository;

    public OrderPartService(@Qualifier("orderPartRepository")OrderPartRepository orderPartRepository) {
        this.orderPartRepository = orderPartRepository;
    }

    public List<OrderPartDto> findByOrderId(Long orderId) {
        return orderPartRepository.findAllByorder_id(orderId).stream().map(part ->
                new OrderPartDto(part.getId(),
                        part.getPart().getArticle(),
                        part.getPart().getName(),
                        part.getQuantity(),
                        part.getPart().getPrice()
                )
        ).collect(Collectors.toList());
    }
}
