package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.model.Order;
import br.marcoswolf.pulsedelivery.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order createOrder(Order order) {
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());
        return repository.save(order);
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return repository.findById(id);
    }
}
