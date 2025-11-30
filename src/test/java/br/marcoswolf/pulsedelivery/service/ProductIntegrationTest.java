package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.CategoryDTO;
import br.marcoswolf.pulsedelivery.dto.ProductDTO;
import br.marcoswolf.pulsedelivery.model.Category;
import br.marcoswolf.pulsedelivery.model.Product;
import br.marcoswolf.pulsedelivery.repository.CategoryRepository;
import br.marcoswolf.pulsedelivery.repository.ProductRepository;
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
public class ProductIntegrationTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category lanchesCategory;
    private Category pizzasCategory;
    private Category bebidasCategory;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        lanchesCategory = categoryRepository.save(new Category(null, "Lanches", true));
        pizzasCategory = categoryRepository.save(new Category(null, "Pizzas", true));
        bebidasCategory = categoryRepository.save(new Category(null, "Bebidas", true));
    }

    @Test
    void shouldSaveAndReturnProduct() {
        ProductDTO productDTO = createProductDTO(
                "Hambúrguer Artesanal",
                "Delicioso hambúrguer artesanal",
                new BigDecimal("29.90"),
                50,
                lanchesCategory.getId()
        );

        Product savedProduct = productService.createProduct(productDTO);

        assertNotNull(savedProduct.getId());
        assertEquals("Hambúrguer Artesanal", savedProduct.getName());
        assertEquals("Delicioso hambúrguer artesanal", savedProduct.getDescription());
        assertEquals(new BigDecimal("29.90"), savedProduct.getPrice());
        assertEquals(50, savedProduct.getStock());
        assertEquals("Lanches", savedProduct.getCategory().getName());
        assertTrue(savedProduct.getActive());

        Optional<Product> foundProduct = productService.getProductById(savedProduct.getId());

        assertTrue(foundProduct.isPresent());
        assertEquals("Hambúrguer Artesanal", foundProduct.get().getName());
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        ProductDTO productDTO = createProductDTO(
                "Pizza Margherita",
                "Pizza clássica",
                new BigDecimal("45.00"),
                30,
                pizzasCategory.getId()
        );
        Product savedProduct = productService.createProduct(productDTO);

        ProductDTO updateDTO = new ProductDTO(
                savedProduct.getId(),
                "Pizza Margherita Premium",
                "Pizza com ingredientes premium",
                new BigDecimal("55.00"),
                25,
                new CategoryDTO(pizzasCategory.getId(), pizzasCategory.getName(), pizzasCategory.getActive()),
                true
        );

        Product updated = productService.updateProduct(savedProduct.getId(), updateDTO);

        assertNotNull(updated);
        assertEquals(savedProduct.getId(), updated.getId());
        assertEquals("Pizza Margherita Premium", updated.getName());
        assertEquals("Pizza com ingredientes premium", updated.getDescription());
        assertEquals(new BigDecimal("55.00"), updated.getPrice());
        assertEquals(25, updated.getStock());

        Optional<Product> foundProduct = productRepository.findById(updated.getId());
        assertTrue(foundProduct.isPresent());
        assertEquals("Pizza Margherita Premium", foundProduct.get().getName());
        assertEquals(new BigDecimal("55.00"), foundProduct.get().getPrice());
    }

    @Test
    void shouldToggleProductActive() {
        ProductDTO productDTO = createProductDTO(
                "Batata Frita",
                "Batata frita crocante",
                new BigDecimal("12.50"),
                100,
                lanchesCategory.getId()
        );
        Product savedProduct = productService.createProduct(productDTO);

        assertTrue(savedProduct.getActive());

        Product toggled = productService.toggleActive(savedProduct.getId());
        assertFalse(toggled.getActive());

        Product toggledAgain = productService.toggleActive(savedProduct.getId());
        assertTrue(toggledAgain.getActive());

        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());
        assertTrue(foundProduct.isPresent());
        assertTrue(foundProduct.get().getActive());
    }

    @Test
    void shouldReturnAllProducts() {
        productService.createProduct(createProductDTO("Hambúrguer", "Desc", new BigDecimal("25.00"), 50, lanchesCategory.getId()));
        productService.createProduct(createProductDTO("Pizza", "Desc", new BigDecimal("40.00"), 30, pizzasCategory.getId()));
        productService.createProduct(createProductDTO("Refrigerante", "Desc", new BigDecimal("5.00"), 200, bebidasCategory.getId()));

        List<Product> products = productService.getAllProducts();

        assertEquals(3, products.size());
    }

    @Test
    void shouldReturnProductsByCategoryId() {
        productService.createProduct(createProductDTO("Hambúrguer", "Desc", new BigDecimal("25.00"), 50, lanchesCategory.getId()));
        productService.createProduct(createProductDTO("X-Bacon", "Desc", new BigDecimal("28.00"), 40, lanchesCategory.getId()));
        productService.createProduct(createProductDTO("Pizza", "Desc", new BigDecimal("40.00"), 30, pizzasCategory.getId()));

        List<Product> lanches = productService.getProductsByCategoryId(lanchesCategory.getId());

        assertEquals(2, lanches.size());
        assertTrue(lanches.stream().allMatch(p -> p.getCategory().getId().equals(lanchesCategory.getId())));
    }

    @Test
    void shouldReturnOnlyActiveProducts() {
        Product product1 = productService.createProduct(createProductDTO("Hambúrguer", "Desc", new BigDecimal("25.00"), 50, lanchesCategory.getId()));
        Product product2 = productService.createProduct(createProductDTO("Pizza", "Desc", new BigDecimal("40.00"), 30, pizzasCategory.getId()));
        Product product3 = productService.createProduct(createProductDTO("Refrigerante", "Desc", new BigDecimal("5.00"), 200, bebidasCategory.getId()));

        productService.toggleActive(product2.getId());

        List<Product> activeProducts = productService.getActiveProducts();

        assertEquals(2, activeProducts.size());
        assertTrue(activeProducts.stream().allMatch(Product::getActive));
        assertFalse(activeProducts.stream().anyMatch(p -> p.getId().equals(product2.getId())));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        ProductDTO updateDTO = new ProductDTO(
                999L,
                "Produto Inexistente",
                "Descrição",
                new BigDecimal("10.00"),
                10,
                new CategoryDTO(lanchesCategory.getId(), "Lanches", true),
                true
        );

        assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(999L, updateDTO));
    }

    @Test
    void shouldReturnEmptyWhenProductNotFound() {
        Optional<Product> result = productService.getProductById(999L);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldUpdateStockWhenUpdatingProduct() {
        ProductDTO productDTO = createProductDTO("Hambúrguer", "Desc", new BigDecimal("25.00"), 50, lanchesCategory.getId());
        Product savedProduct = productService.createProduct(productDTO);

        assertEquals(50, savedProduct.getStock());

        ProductDTO updateDTO = new ProductDTO(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                30,
                new CategoryDTO(lanchesCategory.getId(), lanchesCategory.getName(), lanchesCategory.getActive()),
                savedProduct.getActive()
        );

        Product updated = productService.updateProduct(savedProduct.getId(), updateDTO);

        assertEquals(30, updated.getStock());
    }

    private ProductDTO createProductDTO(String name, String description, BigDecimal price, Integer stock, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

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