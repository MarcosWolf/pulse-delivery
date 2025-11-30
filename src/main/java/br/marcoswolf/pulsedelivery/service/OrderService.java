package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.OrderDTO;
import br.marcoswolf.pulsedelivery.dto.OrderUpdateDTO;
import br.marcoswolf.pulsedelivery.mapper.OrderMapper;
import br.marcoswolf.pulsedelivery.model.Order;
import br.marcoswolf.pulsedelivery.model.OrderStatus;
import br.marcoswolf.pulsedelivery.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    public OrderService(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        Order order = mapper.toEntity(orderDTO);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        return repository.save(order);
    }

    @Transactional
    public Order updateOrder(Long id, OrderUpdateDTO updateDTO) {
        Order existingOrder = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        mapper.updateOrderFromDTO(updateDTO, existingOrder);

        return repository.save(existingOrder);
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return repository.findById(id);
    }
}