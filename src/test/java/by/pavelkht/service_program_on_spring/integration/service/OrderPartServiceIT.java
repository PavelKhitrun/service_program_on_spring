package by.pavelkht.service_program_on_spring.integration.service;

import by.pavelkht.service_program_on_spring.ServiceProgramOnSpringApplication;
import by.pavelkht.service_program_on_spring.database.entity.OrderStatus;
import by.pavelkht.service_program_on_spring.dto.OrderDto;
import by.pavelkht.service_program_on_spring.dto.OrderPartDto;
import by.pavelkht.service_program_on_spring.service.OrderPartService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ServiceProgramOnSpringApplication.class)
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class OrderPartServiceIT {

    @MockitoBean
    private final OrderPartService orderPartService;

    @Test
    void findByOrderId(){
        when(orderPartService.findByOrderId(anyLong()))
                .thenReturn(List.of(new OrderPartDto(1L, 111222, "testPart", 1, BigDecimal.valueOf(100.00))));

        var result = orderPartService.findByOrderId(1L);
        assertEquals(OrderPartDto.class, result.get(0).getClass(), "Возвращает класс OrderPartDto");
        assertTrue(result.get(0).partName().contains("testPart"));
    }
}
