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

    void shouldUpdateCustomerBasicInfoSuccessfully() {
        CustomerDTO customerDTO = createCustomerDTO();
        Customer saved = repository.saveAndFlush(mapper.toEntity(customerDTO));

        CustomerDTO updateDTO = new CustomerDTO(
                null,
                "João Silva",
                "joao@gmail.com",
                null  // address null = não será atualizado
        );

        given()
                .contentType(ContentType.JSON)
                .body(updateDTO)
                .when()
                .patch("/{id}", saved.getId())  // PATCH ao invés de PUT
                .then()
                .statusCode(200)
                .body("name", equalTo("João Silva"))
                .body("email", equalTo("joao@gmail.com"))
                .body("address.street", equalTo("Rua Lobo"))  // Mantém o address original
                .body("address.number", equalTo("123"));
    }

    @Test
    void shouldUpdateCustomerAddressSuccessfully() {
        CustomerDTO customerDTO = createCustomerDTO();
        Customer saved = repository.saveAndFlush(mapper.toEntity(customerDTO));

        AddressDTO newAddress = new AddressDTO(
                "Avenida Paulista",
                "1000",
                "Apto 501",
                "Bela Vista",
                "São Paulo",
                "SP",
                "01310-100",
                "Brasil"
        );

        given()
                .contentType(ContentType.JSON)
                .body(newAddress)
                .when()
                .patch("/{id}/address", saved.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Marcos Vinícios"))  // Nome mantido
                .body("email", equalTo("viniciosramos.dev@gmail.com"))  // Email mantido
                .body("address.street", equalTo("Avenida Paulista"))  // Address atualizado
                .body("address.number", equalTo("1000"))
                .body("address.complement", equalTo("Apto 501"))
                .body("address.city", equalTo("São Paulo"));
    }

    @Test
    void shouldReturnAllCustomers() {
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
    void shouldFindCustomerById() {
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
    void shouldReturn404WhenCustomerNotFound() {
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