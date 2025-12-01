package br.marcoswolf.pulsedelivery.controller;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Disabled("Em desenvolvimento")
public class OrderRestAssuredTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private OrderRepository repository;
//
//    @Autowired
//    private OrderMapper mapper;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    private Product product1;
//    private Product product2;
//
//    @BeforeEach
//    void setup() {
//        RestAssured.port = port;
//        RestAssured.basePath = "/orders";
//
//        repository.deleteAll();
//        productRepository.deleteAll();
//        categoryRepository.deleteAll();
//
//        createProducts();
//    }
//
//    @Test
//    void shouldCreateOrderSuccessfully() {
//        OrderDTO orderDTO = createOrderDTO();
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(orderDTO)
//                .when()
//                .post()
//                .then()
//                .statusCode(201)
//                .header("Location", notNullValue())
//                .header("Location", matchesPattern(".*/orders/\\d+"))
//                .body("customer.name", equalTo("Marcos Vinícios"))
//                .body("id", notNullValue());
//    }
//
//    @Test
//    void shouldUpdateOrderSuccessfully() {
//        OrderDTO orderDTO = createOrderDTO();
//        Order saved = repository.saveAndFlush(mapper.toEntity(orderDTO));
//
//        OrderDTO updateDTO = new OrderDTO(
//                null,
//                null,
//                OrderStatus.DELIVERED,
//                null,
//                List.of()
//        );
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(updateDTO)
//                .when()
//                .put("/{id}", saved.getId())
//                .then()
//                .statusCode(200)
//                .body("customer.name", equalTo("Marcos Vinícios"))
//                .body("status", equalTo("DELIVERED"));
//    }
//
//    @Test
//    void shouldReturnAllOrders() {
//        OrderDTO orderDTO = createOrderDTO();
//        repository.saveAndFlush(mapper.toEntity(orderDTO));
//
//        given()
//                .when()
//                .get()
//                .then()
//                .statusCode(200)
//                .body("$", hasSize(1))
//                .body("[0].customer.name", equalTo("Marcos Vinícios"));
//    }
//
//    @Test
//    void shouldFindOrderById() {
//        OrderDTO orderDTO = createOrderDTO();
//        Order savedOrder = repository.saveAndFlush(mapper.toEntity(orderDTO));
//
//        given()
//                .when()
//                .get("{id}", savedOrder.getId())
//                .then()
//                .statusCode(200)
//                .body("id", equalTo(savedOrder.getId().intValue()))
//                .body("customer.name", equalTo("Marcos Vinícios"));
//    }
//
//    @Test
//    void shouldReturn404WhenOrderNotFound() {
//        given()
//                .when()
//                .get("/999")
//                .then()
//                .statusCode(404);
//    }
//
//    @Test
//    void shouldCreateOrderWithItemsSuccessfully() {
//        OrderDTO orderDTO = createOrderDTO();
//
//        String location = given()
//                .contentType(ContentType.JSON)
//                .body(orderDTO)
//                .when()
//                .post()
//                .then()
//                .statusCode(201)
//                .header("Location", notNullValue())
//                .body("id", notNullValue())
//                .body("customer.name", equalTo("Marcos Vinícios"))
//                .body("orderItems", hasSize(2))
//                .body("orderItems[0].quantity", equalTo(2))
//                .body("orderItems[1].quantity", equalTo(1))
//                .extract()
//                .header("Location");
//
//        String orderId = location.substring(location.lastIndexOf("/") + 1);
//
//        given()
//                .when()
//                .get("/{id}", orderId)
//                .then()
//                .statusCode(200)
//                .body("orderItems", hasSize(2))
//                .body("orderItems[0].quantity", equalTo(2))
//                .body("orderItems[1].quantity", equalTo(1));
//    }
//
//    // --- Helper Methods ---
//
//    private AddressDTO createAddressDTO() {
//        return new AddressDTO(
//                "Rua Lobo",
//                "123",
//                null,
//                null,
//                "Cidade X",
//                "São Paulo",
//                "00000-000",
//                "Brasil"
//        );
//    }
//
//    private CustomerDTO createCustomerDTO() {
//        return new CustomerDTO(
//                null,
//                "Marcos Vinícios",
//                "viniciosramos.dev@gmail.com",
//                createAddressDTO()
//        );
//    }
//
//    private List<OrderItemDTO> createOrderItems() {
//        OrderItemDTO item1 = new OrderItemDTO(null, product1.getId(), 2);
//        OrderItemDTO item2 = new OrderItemDTO(null, product2.getId(), 1);
//        return List.of(item1, item2);
//    }
//
//    private OrderDTO createOrderDTO() {
//        return new OrderDTO(
//                null,
//                createCustomerDTO(),
//                OrderStatus.CREATED,
//                null,
//                createOrderItems()
//        );
//    }
//
//    private void createProducts() {
//        Category category = new Category();
//        category.setName("Lanches");
//        categoryRepository.save(category);
//
//        product1 = new Product();
//        product1.setName("Hambúrguer Artesanal");
//        product1.setDescription("Descrição");
//        product1.setPrice(new BigDecimal("29.90"));
//        product1.setCategory(category);
//        productRepository.save(product1);
//
//        product2 = new Product();
//        product2.setName("Batata Frita");
//        product2.setDescription("Descrição");
//        product2.setPrice(new BigDecimal("12.50"));
//        product2.setCategory(category);
//        productRepository.save(product2);
//    }
}