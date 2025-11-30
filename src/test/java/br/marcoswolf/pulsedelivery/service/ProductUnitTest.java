package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.CategoryDTO;
import br.marcoswolf.pulsedelivery.dto.ProductDTO;
import br.marcoswolf.pulsedelivery.mapper.ProductMapper;
import br.marcoswolf.pulsedelivery.model.Category;
import br.marcoswolf.pulsedelivery.model.Product;
import br.marcoswolf.pulsedelivery.repository.ProductRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled("Em desenvolvimento")
public class ProductUnitTest {
    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService service;

    @Test
    void shouldCreateProductSuccessfully() {
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Lanches", true);
        ProductDTO productDTO = new ProductDTO(
                null,
                "Hambúrguer Artesanal",
                "Hambúrguer com carne angus, queijo cheddar e bacon",
                new BigDecimal("29.90"),
                50,
                categoryDTO,
                true
        );

        Category category = new Category(1L, "Lanches", true);
        Product product = new Product();
        product.setName("Hambúrguer Artesanal");
        product.setDescription("Hambúrguer com carne angus, queijo cheddar e bacon");
        product.setPrice(new BigDecimal("29.90"));
        product.setStock(50);
        product.setCategory(category);
        product.setActive(true);

        when(mapper.toEntity(productDTO)).thenReturn(product);

        Product savedEntity = new Product();
        savedEntity.setId(1L);
        savedEntity.setName("Hambúrguer Artesanal");
        savedEntity.setDescription("Hambúrguer com carne angus, queijo cheddar e bacon");
        savedEntity.setPrice(new BigDecimal("29.90"));
        savedEntity.setStock(50);
        savedEntity.setCategory(category);
        savedEntity.setActive(true);

        when(repository.save(any(Product.class))).thenReturn(savedEntity);

        Product result = service.createProduct(productDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Hambúrguer Artesanal", result.getName());
        assertEquals(new BigDecimal("29.90"), result.getPrice());
        assertEquals(50, result.getStock());
        assertEquals("Lanches", result.getCategory().getName());
        assertTrue(result.getActive());

        verify(mapper).toEntity(productDTO);
        verify(repository).save(any(Product.class));
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        Category category = new Category(1L, "Lanches", true);

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Hambúrguer Artesanal");
        existingProduct.setDescription("Descrição antiga");
        existingProduct.setPrice(new BigDecimal("29.90"));
        existingProduct.setStock(50);
        existingProduct.setCategory(category);
        existingProduct.setActive(true);

        when(repository.findById(1L)).thenReturn(Optional.of(existingProduct));

        CategoryDTO categoryDTO = new CategoryDTO(1L, "Lanches", true);
        ProductDTO updateDTO = new ProductDTO(
                1L,
                "Hambúrguer Premium",
                "Nova descrição premium",
                new BigDecimal("34.90"),
                40,
                categoryDTO,
                true
        );

        doAnswer(invocation -> {
            Product product = invocation.getArgument(1);
            product.setName("Hambúrguer Premium");
            product.setDescription("Nova descrição premium");
            product.setPrice(new BigDecimal("34.90"));
            product.setStock(40);
            return null;
        }).when(mapper).updateProductFromDTO(updateDTO, existingProduct);

        when(repository.save(any(Product.class))).thenReturn(existingProduct);

        Product result = service.updateProduct(1L, updateDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Hambúrguer Premium", result.getName());
        assertEquals("Nova descrição premium", result.getDescription());
        assertEquals(new BigDecimal("34.90"), result.getPrice());
        assertEquals(40, result.getStock());

        verify(repository).findById(1L);
        verify(mapper).updateProductFromDTO(updateDTO, existingProduct);
        verify(repository).save(existingProduct);
    }

    @Test
    void shouldToggleProductActive() {
        Category category = new Category(1L, "Lanches", true);
        Product product = new Product();
        product.setId(1L);
        product.setName("Hambúrguer");
        product.setCategory(category);
        product.setActive(true);

        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenReturn(product);

        Product result = service.toggleActive(1L);

        assertNotNull(result);
        assertFalse(result.getActive());

        verify(repository).findById(1L);
        verify(repository).save(product);
    }

    @Test
    void shouldThrowExceptionWhenDtoIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.createProduct(null));
        verifyNoInteractions(repository, mapper);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundOnUpdate() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        CategoryDTO categoryDTO = new CategoryDTO(1L, "Lanches", true);
        ProductDTO updateDTO = new ProductDTO(
                999L, "Produto", "Descrição", new BigDecimal("10.00"), 10, categoryDTO, true
        );

        assertThrows(IllegalArgumentException.class,
                () -> service.updateProduct(999L, updateDTO));

        verify(repository).findById(999L);
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundOnToggle() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> service.toggleActive(999L));

        verify(repository).findById(999L);
        verify(repository, never()).save(any());
    }

    @Test
    void shouldGetProductByIdSuccessfully() {
        Category category = new Category(1L, "Lanches", true);
        Product product = new Product();
        product.setId(1L);
        product.setName("Hambúrguer");
        product.setCategory(category);

        when(repository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = service.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Hambúrguer", result.get().getName());
        verify(repository).findById(1L);
    }

    @Test
    void shouldReturnEmptyWhenProductNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<Product> result = service.getProductById(2L);

        assertTrue(result.isEmpty());
        verify(repository).findById(2L);
    }

    @Test
    void shouldReturnListOfProducts() {
        Category category = new Category(1L, "Lanches", true);

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Hambúrguer");
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Pizza");
        product2.setCategory(category);

        when(repository.findAll()).thenReturn(List.of(product1, product2));

        List<Product> result = service.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Hambúrguer", result.get(0).getName());
        assertEquals("Pizza", result.get(1).getName());
        verify(repository).findAll();
    }

    @Test
    void shouldReturnProductsByCategoryId() {
        Category category = new Category(1L, "Lanches", true);

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Hambúrguer");
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("X-Bacon");
        product2.setCategory(category);

        when(repository.findByCategoryId(1L)).thenReturn(List.of(product1, product2));

        List<Product> result = service.getProductsByCategoryId(1L);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> p.getCategory().getId().equals(1L)));
        verify(repository).findByCategoryId(1L);
    }

    @Test
    void shouldReturnOnlyActiveProducts() {
        Category category = new Category(1L, "Lanches", true);

        Product product1 = new Product();
        product1.setId(1L);
        product1.setActive(true);
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setActive(true);
        product2.setCategory(category);

        when(repository.findByActive(true)).thenReturn(List.of(product1, product2));

        List<Product> result = service.getActiveProducts();

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(Product::getActive));
        verify(repository).findByActive(true);
    }
}