package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.CategoryDTO;
import br.marcoswolf.pulsedelivery.mapper.CategoryMapper;
import br.marcoswolf.pulsedelivery.model.Category;
import br.marcoswolf.pulsedelivery.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        Category category = mapper.toEntity(categoryDTO);

        return repository.save(category);
    }

    public Category updateCategory(Long id, CategoryDTO updatedDTO) {
        Category existingCategory = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("Category not found")));

        mapper.updateCategoryFromDTO(updatedDTO, existingCategory);

        return repository.save(existingCategory);
    }

    public Category toggleActive(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        category.setActive(!category.getActive());

        return repository.save(category);
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return repository.findById(id);
    }
}
