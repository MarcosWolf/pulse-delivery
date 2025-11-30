package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.OrderDTO;
import br.marcoswolf.pulsedelivery.dto.OrderUpdateDTO;
import br.marcoswolf.pulsedelivery.kafka.dto.OrderCreatedEventDTO;
import br.marcoswolf.pulsedelivery.kafka.producer.KafkaEventProducer;
import br.marcoswolf.pulsedelivery.mapper.OrderMapper;
import br.marcoswolf.pulsedelivery.model.Order;
import br.marcoswolf.pulsedelivery.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Manages customer orders")
public class OrderController {
    private final OrderService service;
    private final OrderMapper mapper;
    private KafkaEventProducer kafkaEventProducer;

    public OrderController(OrderService service, OrderMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Creates a new order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"customerName\":\"John Doe\",\"status\":\"CREATED\"}")
                    )),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<OrderDTO> createOrder(
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            @RequestBody(
                    description = "DTO containing order details",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class),
                            examples = @ExampleObject(value = "{\"customerName\":\"John Doe\",\"status\":\"CREATED\"}")
                    )
            )
            OrderDTO orderDTO) {

        Order order = service.createOrder(orderDTO);
        OrderDTO dto = mapper.toDTO(order);

        OrderCreatedEventDTO event = new OrderCreatedEventDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getCustomer().getAddress().getStreet(),
                order.getCreatedAt()
        );
        kafkaEventProducer.sendOrderCreatedEvent(event);

        return ResponseEntity
                .created(URI.create("/orders/" + order.getId()))
                .body(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an existing order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"customerName\":\"Jane Doe\",\"status\":\"COMPLETED\"}")
                    )),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable Long id,
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            @RequestBody(
                    description = "DTO containing fields to update",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderUpdateDTO.class),
                            examples = @ExampleObject(value = "{\"status\":\"COMPLETED\"}")
                    )
            )
            OrderUpdateDTO orderDTO) {

        Order updatedOrder = service.updateOrder(id, orderDTO);
        return ResponseEntity.ok(mapper.toDTO(updatedOrder));
    }

    @GetMapping
    @Operation(summary = "Returns all orders")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of orders",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class)
                    ))
    })
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = service.getAllOrders();
        List<OrderDTO> dtos = orders.stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns an order by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"customerName\":\"John Doe\",\"status\":\"CREATED\"}")
                    )),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return service.getOrderById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}