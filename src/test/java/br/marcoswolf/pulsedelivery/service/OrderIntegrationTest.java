package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.*;
import br.marcoswolf.pulsedelivery.model.Order;
import br.marcoswolf.pulsedelivery.model.OrderStatus;
import br.marcoswolf.pulsedelivery.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class OrderIntegrationTest {
    @Autowired
    private OrderService service;

    @Autowired
    private OrderRepository repository;

    @Test
    void shouldSaveAndReturnOrder() {
        OrderDTO orderDTO = createOrderDTO();

        Order savedOrder = service.createOrder(orderDTO);

        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getCustomer());
        assertEquals("Marcos Vinícios", savedOrder.getCustomer().getName());
        assertEquals("viniciosramos.dev@gmail.com", savedOrder.getCustomer().getEmail());
        assertEquals(OrderStatus.CREATED, savedOrder.getStatus());

        Optional<Order> foundOrder = service.getOrderById(savedOrder.getId());

        assertTrue(foundOrder.isPresent());
        assertEquals("Marcos Vinícios", foundOrder.get().getCustomer().getName());
        assertEquals("Rua Lobo", foundOrder.get().getCustomer().getAddress().getStreet());
        assertEquals("123", foundOrder.get().getCustomer().getAddress().getNumber());
    }

    @Test
    void shouldUpdateOrderSuccessfully() {
        OrderDTO orderDTO = createOrderDTO();

        Order savedOrder = service.createOrder(orderDTO);

        OrderUpdateDTO dtoToUpdate = new OrderUpdateDTO(
                OrderStatus.DELIVERED
        );

        Order updated = service.updateOrder(savedOrder.getId(), dtoToUpdate);

        assertNotNull(updated);
        assertEquals(savedOrder.getId(), updated.getId());
        assertEquals(OrderStatus.DELIVERED, updated.getStatus());
        assertEquals("Marcos Vinícios", updated.getCustomer().getName());

        Optional<Order> foundOrder = repository.findById(updated.getId());
        assertTrue(foundOrder.isPresent());
        assertEquals(OrderStatus.DELIVERED, foundOrder.get().getStatus());
    }

    @Test
    @Transactional
    void shouldSaveOrderWithItems() {
        OrderDTO orderDTO = createOrderDTO();

        Order savedOrder = service.createOrder(orderDTO);

        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getOrderItems());
        assertEquals(2, savedOrder.getOrderItems().size());

        assertEquals("Hambúrguer Artesanal", savedOrder.getOrderItems().get(0).getProductName());
        assertEquals(2, savedOrder.getOrderItems().get(0).getQuantity());
        assertEquals(new BigDecimal("29.90"), savedOrder.getOrderItems().get(0).getPrice());

        assertEquals("Batata Frita", savedOrder.getOrderItems().get(1).getProductName());
        assertEquals(1, savedOrder.getOrderItems().get(1).getQuantity());
        assertEquals(new BigDecimal("12.50"), savedOrder.getOrderItems().get(1).getPrice());

        Optional<Order> found = repository.findById(savedOrder.getId());
        assertTrue(found.isPresent());
        assertEquals(2, found.get().getOrderItems().size());
    }

    private AddressDTO createAddressDTO() {
        return new AddressDTO(
                "Rua Lobo",
                "123",
                null,
                null,
                "Cidade X",
                "São Paulo",
                "00000-000",
                "Brasil"
        );
    }

    private CustomerDTO createCustomerDTO() {
        return new CustomerDTO(
                null,
                "Marcos Vinícios",
                "viniciosramos.dev@gmail.com",
                createAddressDTO()
        );
    }

    private List<OrderItemDTO> createOrderItems() {
        OrderItemDTO item1 = new OrderItemDTO(
                null,
                "Hambúrguer Artesanal",
                2,
                new BigDecimal("29.90")
        );

        OrderItemDTO item2 = new OrderItemDTO(
                null,
                "Batata Frita",
                1,
                new BigDecimal("12.50")
        );

        return List.of(item1, item2);
    }

    private OrderDTO createOrderDTO() {
        return new OrderDTO(
                null,
                createCustomerDTO(),
                OrderStatus.CREATED,
                null,
                createOrderItems()
        );
    }
}