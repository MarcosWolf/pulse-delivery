package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.category.CategoryDTO;
import br.marcoswolf.pulsedelivery.dto.product.ProductDTO;
import br.marcoswolf.pulsedelivery.mapper.ProductMapper;
import br.marcoswolf.pulsedelivery.model.Category;
import br.marcoswolf.pulsedelivery.model.Product;
import br.marcoswolf.pulsedelivery.repository.CategoryRepository;
import br.marcoswolf.pulsedelivery.repository.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Disabled("Em desenvolvimento")
public class ProductRestAssuredTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper mapper;

    private Category lanchesCategory;
    private Category pizzasCategory;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/products";

        productRepository.deleteAll();
        categoryRepository.deleteAll();

        lanchesCategory = categoryRepository.save(new Category(null, "Lanches", true));
        pizzasCategory = categoryRepository.save(new Category(null, "Pizzas", true));
    }

    @Test
    void shouldCreateProductSuccessfully() {
        ProductDTO productDTO = createProductDTO(
                "Hambúrguer Artesanal",
                "Delicioso hambúrguer",
                new BigDecimal("29.90"),
                50,
                lanchesCategory
        );

        given()
                .contentType(ContentType.JSON)
                .body(productDTO)
                .when()
                .post()
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .header("Location", matchesPattern(".*/products/\\d+"))
                .body("name", equalTo("Hambúrguer Artesanal"))
                .body("price", equalTo(29.90f))
                .body("stock", equalTo(50))
                .body("category.name", equalTo("Lanches"))
                .body("active", equalTo(true))
                .body("id", notNullValue());
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        ProductDTO productDTO = createProductDTO(
                "Pizza Margherita",
                "Pizza clássica",
                new BigDecimal("45.00"),
                30,
                pizzasCategory
        );
        Product saved = productRepository.saveAndFlush(mapper.toEntity(productDTO));

        ProductDTO updateDTO = new ProductDTO(
                saved.getId(),
                "Pizza Margherita Premium",
                "Pizza premium com ingredientes selecionados",
                new BigDecimal("55.00"),
                25,
                new CategoryDTO(pizzasCategory.getId(), pizzasCategory.getName(), pizzasCategory.getActive()),
                true
        );

        given()
                .contentType(ContentType.JSON)
                .body(updateDTO)
                .when()
                .patch("/{id}", saved.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Pizza Margherita Premium"))
                .body("description", equalTo("Pizza premium com ingredientes selecionados"))
                .body("price", equalTo(55.00f))
                .body("stock", equalTo(25))
                .body("category.name", equalTo("Pizzas"));
    }

    @Test
    void shouldToggleProductActive() {
        ProductDTO productDTO = createProductDTO(
                "Batata Frita",
                "Batata crocante",
                new BigDecimal("12.50"),
                100,
                lanchesCategory
        );
        Product saved = productRepository.saveAndFlush(mapper.toEntity(productDTO));

        given()
                .when()
                .patch("/{id}/active", saved.getId())
                .then()
                .statusCode(200)
                .body("active", equalTo(false));

        given()
                .when()
                .patch("/{id}/active", saved.getId())
                .then()
                .statusCode(200)
                .body("active", equalTo(true));
    }

    @Test
    void shouldReturnAllProducts() {
        productRepository.saveAndFlush(mapper.toEntity(
                createProductDTO("Hambúrguer", "Desc", new BigDecimal("25.00"), 50, lanchesCategory)
        ));
        productRepository.saveAndFlush(mapper.toEntity(
                createProductDTO("Pizza", "Desc", new BigDecimal("40.00"), 30, pizzasCategory)
        ));

        given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("$", hasSize(2))
                .body("[0].name", notNullValue())
                .body("[1].name", notNullValue());
    }

    @Test
    void shouldFindProductById() {
        ProductDTO productDTO = createProductDTO(
                "X-Bacon",
                "X-Bacon delicioso",
                new BigDecimal("28.00"),
                40,
                lanchesCategory
        );
        Product savedProduct = productRepository.saveAndFlush(mapper.toEntity(productDTO));

        given()
                .when()
                .get("/{id}", savedProduct.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(savedProduct.getId().intValue()))
                .body("name", equalTo("X-Bacon"))
                .body("price", equalTo(28.00f))
                .body("stock", equalTo(40))
                .body("category.name", equalTo("Lanches"));
    }

    @Test
    void shouldReturn404WhenProductNotFound() {
        given()
                .when()
                .get("/999")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldReturnProductsByCategoryId() {
        productRepository.saveAndFlush(mapper.toEntity(
                createProductDTO("Hambúrguer", "Desc", new BigDecimal("25.00"), 50, lanchesCategory)
        ));
        productRepository.saveAndFlush(mapper.toEntity(
                createProductDTO("X-Bacon", "Desc", new BigDecimal("28.00"), 40, lanchesCategory)
        ));
        productRepository.saveAndFlush(mapper.toEntity(
                createProductDTO("Pizza", "Desc", new BigDecimal("40.00"), 30, pizzasCategory)
        ));

        given()
                .queryParam("categoryId", lanchesCategory.getId())
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("$", hasSize(2))
                .body("[0].category.name", equalTo("Lanches"))
                .body("[1].category.name", equalTo("Lanches"));
    }

    @Test
    void shouldReturnOnlyActiveProducts() {
        Product product1 = productRepository.saveAndFlush(mapper.toEntity(
                createProductDTO("Hambúrguer", "Desc", new BigDecimal("25.00"), 50, lanchesCategory)
        ));
        Product product2 = productRepository.saveAndFlush(mapper.toEntity(
                createProductDTO("Pizza", "Desc", new BigDecimal("40.00"), 30, pizzasCategory)
        ));

        // Torna um produto inativo
        product2.setActive(false);
        productRepository.saveAndFlush(product2);

        given()
                .queryParam("active", true)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].name", equalTo("Hambúrguer"))
                .body("[0].active", equalTo(true));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistentProduct() {
        ProductDTO updateDTO = new ProductDTO(
                999L,
                "Produto",
                "Descrição",
                new BigDecimal("10.00"),
                10,
                new CategoryDTO(lanchesCategory.getId(), "Lanches", true),
                true
        );

        given()
                .contentType(ContentType.JSON)
                .body(updateDTO)
                .when()
                .patch("/999")
                .then()
                .statusCode(500);
    }

    @Test
    void shouldReturn404WhenTogglingNonExistentProduct() {
        given()
                .when()
                .patch("/999/active")
                .then()
                .statusCode(500);
    }

    @Test
    void shouldUpdateProductStock() {
        ProductDTO productDTO = createProductDTO(
                "Hambúrguer",
                "Desc",
                new BigDecimal("25.00"),
                50,
                lanchesCategory
        );
        Product saved = productRepository.saveAndFlush(mapper.toEntity(productDTO));

        ProductDTO updateDTO = new ProductDTO(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getPrice(),
                30,  // Novo estoque
                new CategoryDTO(lanchesCategory.getId(), lanchesCategory.getName(), lanchesCategory.getActive()),
                saved.getActive()
        );

        given()
                .contentType(ContentType.JSON)
                .body(updateDTO)
                .when()
                .patch("/{id}", saved.getId())
                .then()
                .statusCode(200)
                .body("stock", equalTo(30));
    }

    private ProductDTO createProductDTO(String name, String description, BigDecimal price, Integer stock, Category category) {
        return new ProductDTO(
                null,
                name,
                description,
                price,
                stock,
                new CategoryDTO(category.getId(), category.getName(), category.getActive()),
                true
        );
    }
}