package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.category.CategoryDTO;
import br.marcoswolf.pulsedelivery.model.Category;
import br.marcoswolf.pulsedelivery.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryIntegrationTest {

    @Autowired
    private CategoryService service;

    @Autowired
    private CategoryRepository repository;

    @Test
    void shouldSaveAndReturnCategory() {
        CategoryDTO dto = new CategoryDTO(null, "Lanches", true);

        Category saved = service.createCategory(dto);

        assertNotNull(saved.getId());
        assertEquals("Lanches", saved.getName());

        Optional<Category> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
    }

    @Test
    void shouldUpdateCategory() {
        CategoryDTO dto = new CategoryDTO(null, "Lanches", false);
        Category saved = service.createCategory(dto);

        CategoryDTO updateDTO = new CategoryDTO(saved.getId(), "Bebidas", true);

        Category updated = service.updateCategory(saved.getId(), updateDTO);

        assertEquals("Bebidas", updated.getName());
    }

    @Test
    void shouldToggleCategoryActive() {
        Category saved = service.createCategory(new CategoryDTO(null, "Doces", true));

        Category toggled = service.toggleActive(saved.getId());

        assertFalse(toggled.getActive());

        Optional<Category> updated = repository.findById(saved.getId());
        assertTrue(updated.isPresent());
        assertFalse(updated.get().getActive());
    }

    @Test
    void shouldGetAllCategories() {
        service.createCategory(new CategoryDTO(null, "Lanches", true));
        service.createCategory(new CategoryDTO(null, "Bebidas", true));

        List<Category> list = service.getAllCategories();

        assertTrue(list.size() >= 2);
    }

    @Test
    void shouldFindById() {
        Category saved = service.createCategory(new CategoryDTO(null, "Lanches", true));

        Optional<Category> found = service.getCategoryById(saved.getId());

        assertTrue(found.isPresent());
    }
}