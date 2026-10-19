package by.pavelkht.service_program_on_spring.service;

import by.pavelkht.service_program_on_spring.database.repository.OrderRepository;
import by.pavelkht.service_program_on_spring.database.repository.UserRepository;
import by.pavelkht.service_program_on_spring.dto.*;
import by.pavelkht.service_program_on_spring.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserMapper userMapper;

    public List<OrderDto> findByUserId(Long userId) {
        return orderRepository.findByUser_Id(userId).stream().map(order ->
                new OrderDto(order.getId(), order.getOrderDate(), order.getStatus())
        ).collect(Collectors.toList());
    }

    public Optional<UserDto> getUserByOrderId(Long orderId) {
        return Optional.ofNullable(userMapper.mapFrom(orderRepository.findById(orderId).get().getUser()));
    }
}
