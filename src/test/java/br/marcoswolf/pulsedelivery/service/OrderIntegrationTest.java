package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.*;
import br.marcoswolf.pulsedelivery.model.Category;
import br.marcoswolf.pulsedelivery.model.Order;
import br.marcoswolf.pulsedelivery.model.OrderStatus;
import br.marcoswolf.pulsedelivery.model.Product;
import br.marcoswolf.pulsedelivery.repository.CategoryRepository;
import br.marcoswolf.pulsedelivery.repository.OrderRepository;
import br.marcoswolf.pulsedelivery.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Disabled("Em desenvolvimento")
public class OrderIntegrationTest {
//
//    @Autowired
//    private OrderService service;
//
//    @Autowired
//    private OrderRepository repository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @BeforeEach
//    void setup() {
//        createProducts();
//    }
//
//    @Test
//    void shouldSaveAndReturnOrder() {
//        OrderDTO orderDTO = createOrderDTO();
//
//        Order savedOrder = service.createOrder(orderDTO);
//
//        assertNotNull(savedOrder.getId());
//        assertNotNull(savedOrder.getCustomer());
//        assertEquals("Marcos Vinícios", savedOrder.getCustomer().getName());
//        assertEquals("viniciosramos.dev@gmail.com", savedOrder.getCustomer().getEmail());
//        assertEquals(OrderStatus.CREATED, savedOrder.getStatus());
//
//        Optional<Order> foundOrder = service.getOrderById(savedOrder.getId());
//        assertTrue(foundOrder.isPresent());
//        assertEquals("Marcos Vinícios", foundOrder.get().getCustomer().getName());
//        assertEquals("Rua Lobo", foundOrder.get().getCustomer().getAddress().getStreet());
//        assertEquals("123", foundOrder.get().getCustomer().getAddress().getNumber());
//    }
//
//    @Test
//    void shouldUpdateOrderSuccessfully() {
//        OrderDTO orderDTO = createOrderDTO();
//        Order savedOrder = service.createOrder(orderDTO);
//
//        OrderUpdateDTO dtoToUpdate = new OrderUpdateDTO(OrderStatus.DELIVERED);
//
//        Order updated = service.updateOrder(savedOrder.getId(), dtoToUpdate);
//
//        assertNotNull(updated);
//        assertEquals(savedOrder.getId(), updated.getId());
//        assertEquals(OrderStatus.DELIVERED, updated.getStatus());
//        assertEquals("Marcos Vinícios", updated.getCustomer().getName());
//
//        Optional<Order> foundOrder = repository.findById(updated.getId());
//        assertTrue(foundOrder.isPresent());
//        assertEquals(OrderStatus.DELIVERED, foundOrder.get().getStatus());
//    }
//
//    @Test
//    @Transactional
//    void shouldSaveOrderWithItems() {
//        OrderDTO orderDTO = createOrderDTO();
//        Order savedOrder = service.createOrder(orderDTO);
//
//        assertNotNull(savedOrder.getId());
//        assertNotNull(savedOrder.getOrderItems());
//        assertEquals(2, savedOrder.getOrderItems().size());
//
//        // Acessando produto dentro do OrderItem
//        assertEquals("Hambúrguer Artesanal", savedOrder.getOrderItems().get(0).getProduct().getName());
//        assertEquals(2, savedOrder.getOrderItems().get(0).getQuantity());
//        assertEquals(new BigDecimal("29.90"), savedOrder.getOrderItems().get(0).getProduct().getPrice());
//
//        assertEquals("Batata Frita", savedOrder.getOrderItems().get(1).getProduct().getName());
//        assertEquals(1, savedOrder.getOrderItems().get(1).getQuantity());
//        assertEquals(new BigDecimal("12.50"), savedOrder.getOrderItems().get(1).getProduct().getPrice());
//
//        Optional<Order> found = repository.findById(savedOrder.getId());
//        assertTrue(found.isPresent());
//        assertEquals(2, found.get().getOrderItems().size());
//    }
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
//        OrderItemDTO item1 = new OrderItemDTO(
//                null, // id
//                1L,   // productId
//                2     // quantity
//        );
//
//        OrderItemDTO item2 = new OrderItemDTO(
//                null,
//                2L,
//                1
//        );
//
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
//        Product product1 = new Product();
//        product1.setName("Hambúrguer Artesanal");
//        product1.setDescription("Descrição");
//        product1.setPrice(new BigDecimal("29.90"));
//        product1.setCategory(category);
//        productRepository.save(product1);
//
//        Product product2 = new Product();
//        product2.setName("Batata Frita");
//        product2.setDescription("Descrição");
//        product2.setPrice(new BigDecimal("12.50"));
//        product2.setCategory(category);
//        productRepository.save(product2);
//    }
}