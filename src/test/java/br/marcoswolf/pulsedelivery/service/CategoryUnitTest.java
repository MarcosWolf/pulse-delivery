package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.CategoryDTO;
import br.marcoswolf.pulsedelivery.mapper.CategoryMapper;
import br.marcoswolf.pulsedelivery.model.Category;
import br.marcoswolf.pulsedelivery.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryUnitTest {

    @Mock
    private CategoryRepository repository;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryService service;

    private CategoryDTO categoryDTO;
    private Category category;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        categoryDTO = new CategoryDTO(null, "Lanches", true);
        category = new Category();
        category.setId(1L);
        category.setName("Lanches");
    }

    @Test
    void shouldCreateCategory() {
        when(mapper.toEntity(categoryDTO)).thenReturn(category);
        when(repository.save(any(Category.class))).thenReturn(category);

        Category result = service.createCategory(categoryDTO);

        assertNotNull(result);
        assertEquals("Lanches", result.getName());

        verify(repository).save(any(Category.class));
    }

    @Test
    void shouldThrowExceptionWhenCreatingNullCategory() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createCategory(null));
    }

    @Test
    void shouldUpdateCategorySuccessfully() {
        Category updated = new Category();
        updated.setId(1L);
        updated.setName("Bebidas");

        when(repository.findById(1L)).thenReturn(Optional.of(category));
        doAnswer(invocation -> {
            CategoryDTO dto = invocation.getArgument(0);
            Category cat = invocation.getArgument(1);
            cat.setName(dto.name());
            return null;
        }).when(mapper).updateCategoryFromDTO(any(), any());

        when(repository.save(any(Category.class))).thenReturn(updated);

        CategoryDTO updateDTO = new CategoryDTO(1L, "Bebidas", true);

        Category result = service.updateCategory(1L, updateDTO);

        assertEquals("Bebidas", result.getName());
        verify(repository).save(any(Category.class));
    }

    @Test
    void shouldToggleCategoryActive() {
        Category category = new Category();
        category.setId(1L);
        category.setActive(true);

        when(repository.findById(1L)).thenReturn(Optional.of(category));
        when(repository.save(any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

        Category result = service.toggleActive(1L);

        assertFalse(result.getActive());

        verify(repository).save(any(Category.class));
    }

    @Test
    void shouldGetAllCategories() {
        when(repository.findAll()).thenReturn(List.of(category));

        List<Category> result = service.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Lanches", result.get(0).getName());
    }

    @Test
    void shouldReturnCategoryById() {
        when(repository.findById(1L)).thenReturn(Optional.of(category));

        Optional<Category> result = service.getCategoryById(1L);

        assertTrue(result.isPresent());
        assertEquals("Lanches", result.get().getName());
    }
}