package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.OrderDTO;
import br.marcoswolf.pulsedelivery.mapper.OrderMapper;
import br.marcoswolf.pulsedelivery.model.Order;
import br.marcoswolf.pulsedelivery.model.OrderStatus;
import br.marcoswolf.pulsedelivery.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderUnitTest {
    @Mock
    private OrderRepository repository;

    @Mock
    private OrderMapper mapper;

    @InjectMocks
    private OrderService service;

    @Test
    void shouldCreateOrderSuccessfully() {
        OrderDTO dto = new OrderDTO(
                null,
                "Marcos Vinícios",
                "Rua Lobo, 123",
                null,
                null
        );

        Order entity = new Order();
        entity.setCustomerName("Marcos Vinícios");
        entity.setAddress("Rua Lobo, 123");

        when(mapper.toEntity(dto)).thenReturn(entity);

        Order savedEntity = new Order();
        savedEntity.setId(1L);
        savedEntity.setCustomerName("Marcos Vinícios");
        savedEntity.setAddress("Rua Lobo, 123");
        savedEntity.setStatus(OrderStatus.CREATED);
        savedEntity.setCreatedAt(LocalDateTime.now());

        when(repository.save(any(Order.class))).thenReturn(savedEntity);

        Order result = service.createOrder(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Marcos Vinícios", result.getCustomerName());
        assertEquals("Rua Lobo, 123", result.getAddress());
        assertEquals(OrderStatus.CREATED, result.getStatus());
        assertNotNull(result.getCreatedAt());

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(repository).save(captor.capture());

        Order captured = captor.getValue();
        assertEquals(OrderStatus.CREATED, captured.getStatus());
        assertNotNull(captured.getCreatedAt());
    }

    @Test
    void shouldUpdateOrderSuccessfully() {
        Order existing = new Order();
        existing.setId(1L);
        existing.setCustomerName("Marcos Vinícios");
        existing.setAddress("Rua Lobo, 123");
        existing.setStatus(OrderStatus.CREATED);
        existing.setCreatedAt(LocalDateTime.now());

        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        OrderDTO updateDTO = new OrderDTO(
                1L,
                "Marcos Vinícios",
                "Rua Lobo, 123",
                OrderStatus.DELIVERED,
                existing.getCreatedAt()
        );

        Order updatedEntity = new Order();
        updatedEntity.setId(1L);
        updatedEntity.setCustomerName("Marcos Vinícios");
        updatedEntity.setAddress("Rua Lobo, 123");
        updatedEntity.setStatus(OrderStatus.DELIVERED);
        updatedEntity.setCreatedAt(existing.getCreatedAt());

        when(mapper.toEntity(updateDTO)).thenReturn(updatedEntity);

        when(repository.save(any(Order.class))).thenReturn(updatedEntity);

        Order updated = service.updateOrder(1L, mapper.toEntity(updateDTO));

        assertNotNull(updated);
        assertEquals(1L, updated.getId());
        assertEquals(OrderStatus.DELIVERED, updated.getStatus());

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
}
