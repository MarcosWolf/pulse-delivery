package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.CategoryDTO;
import br.marcoswolf.pulsedelivery.mapper.CategoryMapper;
import br.marcoswolf.pulsedelivery.model.Category;
import br.marcoswolf.pulsedelivery.repository.CategoryRepository;
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
public class CategoryRestAssuredTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryMapper mapper;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/categories";

        repository.deleteAll();
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        CategoryDTO dto = new CategoryDTO(null, "Lanches", true);

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("name", equalTo("Lanches"))
                .body("active", equalTo(true))
                .body("id", notNullValue());
    }

    @Test
    void shouldUpdateCategorySuccessfully() {
        CategoryDTO dto = new CategoryDTO(null, "Lanches", false);
        Category saved = repository.saveAndFlush(mapper.toEntity(dto));

        CategoryDTO updateDTO = new CategoryDTO(null, "Bebidas", true);

        given()
                .contentType(ContentType.JSON)
                .body(updateDTO)
                .when()
                .patch("/{id}", saved.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Bebidas"))
                .body("active", equalTo(true));
    }

    @Test
    void shouldToggleCategoryActive() {
        Category saved = repository.saveAndFlush(new Category(null, "Doces", true));

        given()
                .when()
                .patch("/{id}/active", saved.getId())
                .then()
                .statusCode(200)
                .body("active", equalTo(false));
    }

    @Test
    void shouldReturnAllCategories() {
        repository.saveAndFlush(new Category(null, "Lanches", true));
        repository.saveAndFlush(new Category(null, "Pizzas", true));

        given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("$", hasSize(2));
    }
}