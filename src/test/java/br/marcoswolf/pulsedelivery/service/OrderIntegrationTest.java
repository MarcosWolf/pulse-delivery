package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.CustomerDTO;
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
            AddressDTO addressDTO = new AddressDTO(
                    "Rua Lobo", "123", null, null, "Cidade X", "São Paulo", "00000-000", "Brasil"
            );
            CustomerDTO customerDTO = new CustomerDTO(
                    null, "Marcos Vinícios", "viniciosramos.dev@gmail.com", addressDTO
            );
            OrderDTO dto = new OrderDTO(
                    null,
                    customerDTO,
                    OrderStatus.CREATED,
                    null
            );

        Order savedOrder = orderService.createOrder(dto);

        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getCustomer());
        assertEquals("Marcos Vinícios", savedOrder.getCustomer().getName());
        assertEquals("viniciosramos.dev@gmail.com", savedOrder.getCustomer().getEmail());
        assertEquals(OrderStatus.CREATED, savedOrder.getStatus());

        Optional<Order> foundOrder = orderService.getOrderById(savedOrder.getId());

        assertTrue(foundOrder.isPresent());
        assertEquals("Marcos Vinícios", foundOrder.get().getCustomer().getName());
        assertEquals("Rua Lobo", foundOrder.get().getCustomer().getAddress().getStreet());
        assertEquals("123", foundOrder.get().getCustomer().getAddress().getNumber());
    }

    @Test
    void shouldUpdateOrderSuccessfully() {
        AddressDTO addressDTO = new AddressDTO(
                "Rua Y", "456", null, null, "Cidade Y", "Rio de Janeiro", "11111-111", "Brasil"
        );
        CustomerDTO customerDTO = new CustomerDTO(
                null, "Cliente X", "cliente@example.com", addressDTO
        );
        OrderDTO dto = new OrderDTO(null, customerDTO, OrderStatus.CREATED, null);

        Order saved = orderService.createOrder(dto);

        Order orderToUpdate = new Order();
        orderToUpdate.setId(saved.getId());
        orderToUpdate.setCustomer(saved.getCustomer());
        orderToUpdate.setStatus(OrderStatus.DELIVERED);
        orderToUpdate.setCreatedAt(saved.getCreatedAt());

        Order updated = orderService.updateOrder(saved.getId(), orderToUpdate);

        assertNotNull(updated);
        assertEquals(saved.getId(), updated.getId());
        assertEquals(OrderStatus.DELIVERED, updated.getStatus());
        assertEquals("Cliente X", updated.getCustomer().getName());

        Optional<Order> foundOrder = orderRepository.findById(updated.getId());
        assertTrue(foundOrder.isPresent());
        assertEquals(OrderStatus.DELIVERED, foundOrder.get().getStatus());
    }
}
