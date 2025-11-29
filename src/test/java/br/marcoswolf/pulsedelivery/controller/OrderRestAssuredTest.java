package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.CustomerDTO;
import br.marcoswolf.pulsedelivery.dto.OrderDTO;
import br.marcoswolf.pulsedelivery.dto.OrderItemDTO;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OrderRestAssuredTest {
    @LocalServerPort
    private int port;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderMapper mapper;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/orders";

        repository.deleteAll();
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        OrderDTO orderDTO = createOrderDTO();

        given()
                .contentType(ContentType.JSON)
                .body(orderDTO)
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
        OrderDTO orderDTO = createOrderDTO();

        Order saved = repository.saveAndFlush(mapper.toEntity(orderDTO));

        OrderDTO updateDTO = new OrderDTO(
                null,
                null,
                OrderStatus.DELIVERED,
                null,
                List.of()
        );

        given()
                .contentType(ContentType.JSON)
                .body(updateDTO)
        .when()
                .put("/{id}", saved.getId())
        .then()
                .statusCode(200)
                .body("customer.name", equalTo("Marcos Vinícios"))
                .body("status", equalTo("DELIVERED"));
    }

    @Test
    void shouldReturnAllOrders() {
        OrderDTO orderDTO = createOrderDTO();

        repository.saveAndFlush(mapper.toEntity(orderDTO));

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
        OrderDTO orderDTO = createOrderDTO();

        Order savedOrder = repository.saveAndFlush(mapper.toEntity(orderDTO));

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

    @Test
    void shouldCreateOrderWithItemsSuccessfully() {
        OrderDTO orderDTO = createOrderDTO();

        String location = given()
                .contentType(ContentType.JSON)
                .body(orderDTO)
                .when()
                .post()
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .body("id", notNullValue())
                .body("customer.name", equalTo("Marcos Vinícios"))
                .body("orderItems", hasSize(2))
                .body("orderItems[0].productName", equalTo("Hambúrguer Artesanal"))
                .body("orderItems[0].quantity", equalTo(2))
                .body("orderItems[0].price", equalTo(29.90f))
                .body("orderItems[1].productName", equalTo("Batata Frita"))
                .body("orderItems[1].quantity", equalTo(1))
                .body("orderItems[1].price", equalTo(12.50f))
                .extract()
                .header("Location");

        String orderId = location.substring(location.lastIndexOf("/") + 1);

        given()
                .when()
                .get("/{id}", orderId)
                .then()
                .statusCode(200)
                .body("orderItems", hasSize(2))
                .body("orderItems[0].productName", equalTo("Hambúrguer Artesanal"))
                .body("orderItems[1].productName", equalTo("Batata Frita"));
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