package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.OrderDTO;
import br.marcoswolf.pulsedelivery.mapper.OrderMapper;
import br.marcoswolf.pulsedelivery.model.Order;
import br.marcoswolf.pulsedelivery.model.OrderStatus;
import br.marcoswolf.pulsedelivery.repository.OrderRepository;
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

    public Order createOrder(OrderDTO orderDTO) {
        if (orderDTO == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        Order order = mapper.toEntity(orderDTO);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        return repository.save(order);
    }

    public Order updateOrder(Long id, Order updatedDto) {
        Order existingOrder = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("Order not found")));

        existingOrder.setCustomerName(updatedDto.getCustomerName());
        existingOrder.setAddress(updatedDto.getAddress());
        if (updatedDto.getStatus() != null) {
            existingOrder.setStatus(updatedDto.getStatus());
        }

        return repository.save(existingOrder);
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return repository.findById(id);
    }
}
