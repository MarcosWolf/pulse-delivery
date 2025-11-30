package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.CustomerDTO;
import br.marcoswolf.pulsedelivery.dto.OrderDTO;
import br.marcoswolf.pulsedelivery.dto.OrderUpdateDTO;
import br.marcoswolf.pulsedelivery.mapper.CustomerMapper;
import br.marcoswolf.pulsedelivery.mapper.OrderItemMapper;
import br.marcoswolf.pulsedelivery.mapper.OrderMapper;
import br.marcoswolf.pulsedelivery.model.*;
import br.marcoswolf.pulsedelivery.repository.OrderRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled("Em desenvolvimento")
public class OrderUnitTest {
    @Mock
    private OrderRepository repository;

    @Mock
    private OrderMapper mapper;

    @Mock
    private OrderItemMapper itemMapper;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private OrderService service;

    @Test
    void shouldCreateOrderSuccessfully() {
        AddressDTO addressDTO = new AddressDTO(
                "Rua Lobo", "123", null, null, "Cidade X", "São Paulo", "00000-000", "Brasil"
        );
        CustomerDTO customerDTO = new CustomerDTO(
                null, "Marcos Vinícios", "viniciosramos.dev@gmail.com", addressDTO
        );
        OrderDTO orderDTO = new OrderDTO(null, customerDTO, OrderStatus.CREATED, null, null);

        Address address = new Address();
        address.setStreet("Rua Lobo");
        address.setNumber("123");
        address.setCity("Cidade X");
        address.setState("São Paulo");
        address.setPostalCode("00000-000");
        address.setCountry("Brasil");

        Customer customer = new Customer();
        customer.setName("Marcos Vinícios");
        customer.setEmail("viniciosramos.dev@gmail.com");
        customer.setAddress(address);

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.CREATED);

        when(mapper.toEntity(orderDTO)).thenReturn(order);

        Order savedEntity = new Order();
        savedEntity.setId(1L);
        savedEntity.setCustomer(customer);
        savedEntity.setStatus(OrderStatus.CREATED);
        savedEntity.setCreatedAt(LocalDateTime.now());

        when(repository.save(any(Order.class))).thenReturn(savedEntity);

        Order result = service.createOrder(orderDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Marcos Vinícios", result.getCustomer().getName());
        assertEquals("viniciosramos.dev@gmail.com", result.getCustomer().getEmail());
        assertEquals(OrderStatus.CREATED, result.getStatus());
        assertNotNull(result.getCreatedAt());

        verify(mapper).toEntity(orderDTO);
        verify(repository).save(any(Order.class));
    }

    @Test
    void shouldUpdateOrderSuccessfully() {
        Address address = new Address();
        address.setStreet("Rua Lobo");
        address.setNumber("123");
        address.setCity("Cidade X");
        address.setState("SP");
        address.setPostalCode("00000-000");
        address.setCountry("Brasil");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Marcos Vinícios");
        customer.setEmail("viniciosramos.dev@gmail.com");
        customer.setAddress(address);

        Order existingOrder = new Order();
        existingOrder.setId(1L);
        existingOrder.setCustomer(customer);
        existingOrder.setStatus(OrderStatus.CREATED);
        existingOrder.setCreatedAt(LocalDateTime.now().minusDays(1));

        when(repository.findById(1L)).thenReturn(Optional.of(existingOrder));

        AddressDTO addressDTO = new AddressDTO("Rua Lobo", "123", null, null, "Cidade X", "SP", "00000-000", "Brasil");
        CustomerDTO customerDTO = new CustomerDTO(1L, "Marcos Vinícios", "viniciosramos.dev@gmail.com", addressDTO);
        OrderUpdateDTO updateDTO = new OrderUpdateDTO(
                OrderStatus.DELIVERED
        );

        Order updatedOrder = new Order();
        updatedOrder.setId(1L);
        updatedOrder.setCustomer(customer);
        updatedOrder.setStatus(OrderStatus.DELIVERED);
        updatedOrder.setCreatedAt(existingOrder.getCreatedAt());

        when(repository.save(any(Order.class))).thenReturn(updatedOrder);

        Order result = service.updateOrder(1L, updateDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(OrderStatus.DELIVERED, result.getStatus());
        assertEquals("Marcos Vinícios", result.getCustomer().getName());

        verify(repository).findById(1L);
        verify(repository).save(any(Order.class));
    }

    @Test
    void shouldThrowExceptionWhenDtoIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.createOrder(null));
        verifyNoInteractions(repository, mapper);
    }

    @Test
    void shouldGetOrderByIdSuccessfully() {
        Order order = new Order();
        order.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = service.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(repository).findById(1L);
    }

    @Test
    void shouldReturnEmptyWhenOrderNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<Order> result = service.getOrderById(2L);

        assertTrue(result.isEmpty());
        verify(repository).findById(2L);
    }

    @Test
    void shouldReturnListOfOrders() {
        when(repository.findAll()).thenReturn(List.of(new Order(), new Order()));

        List<Order> result = service.getAllOrders();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }
}