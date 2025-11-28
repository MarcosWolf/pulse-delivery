package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.CustomerDTO;
import br.marcoswolf.pulsedelivery.dto.OrderDTO;
import br.marcoswolf.pulsedelivery.mapper.OrderMapper;
import br.marcoswolf.pulsedelivery.model.Order;
import br.marcoswolf.pulsedelivery.model.OrderStatus;
import br.marcoswolf.pulsedelivery.repository.OrderRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OrderRestAssuredTest {
    @LocalServerPort
    private int port;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper mapper;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/orders";

        orderRepository.deleteAll();
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        AddressDTO address = new AddressDTO(
                "Rua Lobo", "123", null, null, "Cidade X", "São Paulo", "00000-000", "Brasil"
        );
        CustomerDTO customer = new CustomerDTO(null, "Marcos Vinícios", "viniciosramos.dev@gmail.com", address);
        OrderDTO dto = new OrderDTO(null, customer, OrderStatus.CREATED, LocalDateTime.now());

        given()
                .contentType(ContentType.JSON)
                .body(dto)
        .when()
                .post()
        .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .header("Location", matchesPattern(".*/orders/\\d+"))
                .body("customer.name", equalTo("Marcos Vinícios"))
                .body("id", notNullValue());
    }

    @Test
    void shouldUpdateOrderSuccessfully() {
        AddressDTO address = new AddressDTO(
                "Rua Lobo", "123", null, null, "Cidade X", "São Paulo", "00000-000", "Brasil"
        );
        CustomerDTO customer = new CustomerDTO(null, "Marcos Vinícios", "viniciosramos.dev@gmail.com", address);
        OrderDTO dto = new OrderDTO(null, customer, OrderStatus.CREATED, LocalDateTime.now());

        Order saved = orderRepository.saveAndFlush(mapper.toEntity(dto));

        OrderDTO updateDto = new OrderDTO(
                null,
                null,
                OrderStatus.DELIVERED,
                null
        );

        given()
                .contentType(ContentType.JSON)
                .body(updateDto)
                .when()
                .put("/{id}", saved.getId())
                .then()
                .statusCode(200)
                .body("customer.name", equalTo("Marcos Vinícios"))
                .body("status", equalTo("DELIVERED"));
    }

    @Test
    void shouldReturnAllOrders() {
        AddressDTO address = new AddressDTO(
                "Rua Lobo", "123", null, null, "Cidade X", "São Paulo", "00000-000", "Brasil"
        );
        CustomerDTO customer = new CustomerDTO(null, "Marcos Vinícios", "viniciosramos.dev@gmail.com", address);
        OrderDTO dto = new OrderDTO(null, customer, OrderStatus.CREATED, LocalDateTime.now());

        orderRepository.saveAndFlush(mapper.toEntity(dto));

        given()
        .when()
                .get()
        .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].customer.name", equalTo("Marcos Vinícios"));
    }

    @Test
    void shouldFindOrderbyId() {
        AddressDTO address = new AddressDTO(
                "Rua Lobo", "123", null, null, "Cidade X", "São Paulo", "00000-000", "Brasil"
        );
        CustomerDTO customer = new CustomerDTO(null, "Marcos Vinícios", "viniciosramos.dev@gmail.com", address);
        OrderDTO dto = new OrderDTO(null, customer, OrderStatus.CREATED, LocalDateTime.now());

        Order savedOrder = orderRepository.saveAndFlush(mapper.toEntity(dto));

        given()
        .when()
                .get("{id}", savedOrder.getId())
        .then()
                .statusCode(200)
                .body("id", equalTo(savedOrder.getId().intValue()))
                .body("customer.name", equalTo("Marcos Vinícios"));
    }

    @Test
    void shouldReturn404WhenOrderNotFound() {
        given()
        .when()
                .get("/999")
        .then()
                .statusCode(404);
    }
}
