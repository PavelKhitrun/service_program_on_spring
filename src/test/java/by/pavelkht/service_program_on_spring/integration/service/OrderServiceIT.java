package by.pavelkht.service_program_on_spring.integration.service;

import by.pavelkht.service_program_on_spring.ServiceProgramOnSpringApplication;
import by.pavelkht.service_program_on_spring.database.entity.Order;
import by.pavelkht.service_program_on_spring.database.entity.OrderStatus;
import by.pavelkht.service_program_on_spring.database.entity.User;
import by.pavelkht.service_program_on_spring.database.repository.OrderRepository;
import by.pavelkht.service_program_on_spring.dto.OrderDto;
import by.pavelkht.service_program_on_spring.dto.UserDto;
import by.pavelkht.service_program_on_spring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ServiceProgramOnSpringApplication.class)
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class OrderServiceIT {
    private final OrderService orderService;

    @MockitoBean
    private OrderRepository orderRepository;

    @Test
    void findByUserIdIT() {
        when(orderRepository.findByUser_Id(anyLong()))
                .thenReturn(List.of(new Order(1L, User.builder().build(), LocalDateTime.now(), OrderStatus.PAID)));

        var result = orderService.findByUserId(1L);
        assertEquals(OrderDto.class, result.get(0).getClass(), "Возвращает класс OrderDto");
        assertTrue(result.get(0).status().equals(OrderStatus.PAID));
    }

    @Test
    void getUserByOrderIdIT(){
        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Order(1L, User.builder().id(4L).build(), LocalDateTime.now(), OrderStatus.PAID)));

        var result = orderService.getUserByOrderId(1L);
        assertEquals(UserDto.class, result.get().getClass(), "Возвращает класс UserDto");
        assertTrue(result.get().getId().equals(4L));
    }
}
