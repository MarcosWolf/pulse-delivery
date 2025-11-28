package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.OrderDTO;
import br.marcoswolf.pulsedelivery.mapper.OrderMapper;
import br.marcoswolf.pulsedelivery.model.Order;
import br.marcoswolf.pulsedelivery.model.OrderStatus;
import br.marcoswolf.pulsedelivery.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class OrderIntegrationTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper mapper;

    @Test
    void shouldSaveAndReturnOrder() {
        OrderDTO dto = new OrderDTO(
                null,
                "Marcos Vinícios",
                "Rua Lobo, 123",
                OrderStatus.CREATED,
                LocalDateTime.now()
        );

        Order savedOrder = orderService.createOrder(dto);

        assertNotNull(savedOrder.getId());;

        Optional<Order> foundOrder = orderService.getOrderById(savedOrder.getId());

        assertTrue(foundOrder.isPresent());
        assertEquals("Marcos Vinícios", foundOrder.get().getCustomerName());
    }

    @Test
    void shouldUpdateOrderSuccessfully() {
        OrderDTO dto = new OrderDTO(null, "Cliente X", "Rua Y", OrderStatus.CREATED, null);
        Order saved = orderService.createOrder(dto);

        OrderDTO updateDTO = new OrderDTO(
                saved.getId(),
                saved.getCustomerName(),
                saved.getAddress(),
                OrderStatus.DELIVERED,
                saved.getCreatedAt()
        );

        Order updated = orderService.updateOrder(saved.getId(), mapper.toEntity(updateDTO));

        assertEquals(OrderStatus.DELIVERED, updated.getStatus());
    }
}
