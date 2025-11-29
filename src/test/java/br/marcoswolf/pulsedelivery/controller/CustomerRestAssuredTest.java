package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.CustomerDTO;
import br.marcoswolf.pulsedelivery.mapper.CustomerMapper;
import br.marcoswolf.pulsedelivery.model.Customer;
import br.marcoswolf.pulsedelivery.repository.CustomerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CustomerRestAssuredTest {
    @LocalServerPort
    private int port;

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerMapper mapper;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/customers";

        repository.deleteAll();
    }

    @Test
    void shouldCreateCustomerSuccessfully() {
        CustomerDTO customerDTO = createCustomerDTO();

        given()
                .contentType(ContentType.JSON)
                .body(customerDTO)
        .when()
                .post()
        .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .header("Location", matchesPattern(".*/customers/\\d+"))
                .body("name", equalTo("Marcos Vinícios"))
                .body("id", notNullValue());
    }

    @Test
    void shouldUpdateCustomerSuccessfully() {
        CustomerDTO customerDTO = createCustomerDTO();

        Customer saved = repository.saveAndFlush(mapper.toEntity(customerDTO));

        CustomerDTO updateDTO = new CustomerDTO(
                null,
                "Marcos Vinícios",
                "vinicios@gmail.com",
                null
        );

        given()
                .contentType(ContentType.JSON)
                .body(updateDTO)
        .when()
                .put("/{id}", saved.getId())
        .then()
                .statusCode(200)
                .body("name", equalTo("Marcos Vinícios"))
                .body("email", equalTo("vinicios@gmail.com"));
    }

    @Test
    void shouldReturnAllCostumers() {
        CustomerDTO customerDTO = createCustomerDTO();

        repository.saveAndFlush(mapper.toEntity(customerDTO));

        given()
        .when()
                .get()
        .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].name", equalTo("Marcos Vinícios"));

    }

    @Test
    void shouldFindCostumerById() {
        CustomerDTO customerDTO = createCustomerDTO();

        Customer savedCustomer = repository.saveAndFlush(mapper.toEntity(customerDTO));

        given()
        .when()
                .get("{id}", savedCustomer.getId())
        .then()
                .statusCode(200)
                .body("id", equalTo(savedCustomer.getId().intValue()))
                .body("name", equalTo("Marcos Vinícios"));
    }

    @Test
    void shouldReturn404WhenCostumerNotFound() {
        given()
        .when()
                .get("/999")
        .then()
                .statusCode(404);
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
}