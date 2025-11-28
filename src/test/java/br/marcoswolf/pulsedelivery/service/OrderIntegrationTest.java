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
        AddressDTO addressDTO = createAddressDTO();
        CustomerDTO customerDTO = createCustomerDTO();
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
        AddressDTO addressDTO = createAddressDTO();
        CustomerDTO customerDTO = createCustomerDTO();
        OrderDTO orderDTO = createOrderDTO();

        Order saved = service.createOrder(orderDTO);

        Order orderToUpdate = new Order();
        orderToUpdate.setId(saved.getId());
        orderToUpdate.setCustomer(saved.getCustomer());
        orderToUpdate.setStatus(OrderStatus.DELIVERED);
        orderToUpdate.setCreatedAt(saved.getCreatedAt());

        Order updated = service.updateOrder(saved.getId(), orderToUpdate);

        assertNotNull(updated);
        assertEquals(saved.getId(), updated.getId());
        assertEquals(OrderStatus.DELIVERED, updated.getStatus());
        assertEquals("Marcos Vinícios", updated.getCustomer().getName());

        Optional<Order> foundOrder = repository.findById(updated.getId());
        assertTrue(foundOrder.isPresent());
        assertEquals(OrderStatus.DELIVERED, foundOrder.get().getStatus());
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

    private OrderDTO createOrderDTO() {
        return new OrderDTO(
                null,
                createCustomerDTO(),
                OrderStatus.CREATED,
                null
        );
    }
}
