package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.OrderDTO;
import br.marcoswolf.pulsedelivery.mapper.OrderMapper;
import br.marcoswolf.pulsedelivery.model.Order;
import br.marcoswolf.pulsedelivery.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;
    private final OrderMapper mapper;

    public OrderController(OrderService service, OrderMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = service.createOrder(orderDTO);
        OrderDTO dto = mapper.toDTO(order);
        return ResponseEntity
                .created(URI.create("/orders/" + order.getId()))
                .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        Order updatedEntity = mapper.toEntity(orderDTO);
        Order savedOrder = service.updateOrder(id, updatedEntity);
        return ResponseEntity.ok(mapper.toDTO(savedOrder));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = service.getAllOrders();
        List<OrderDTO> dtos = orders.stream()
                                .map(mapper::toDTO)
                                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return service.getOrderById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
