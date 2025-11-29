package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.CategoryDTO;
import br.marcoswolf.pulsedelivery.mapper.CategoryMapper;
import br.marcoswolf.pulsedelivery.model.Category;
import br.marcoswolf.pulsedelivery.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService service;
    private final CategoryMapper mapper;

    public CategoryController(CategoryService service, CategoryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = service.createCategory(categoryDTO);
        CategoryDTO dto = mapper.toDTO(category);
        return ResponseEntity
                .created(URI.create("/categories/" + category.getId()))
                .body(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        Category savedCategory = service.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(mapper.toDTO(savedCategory));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<CategoryDTO> toggleActive(@PathVariable Long id) {
        Category updated = service.toggleActive(id);
        return ResponseEntity.ok(mapper.toDTO(updated));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = service.getAllCategories();
        List<CategoryDTO> dtos = categories.stream()
                                    .map(mapper::toDTO)
                                    .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return service.getCategoryById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
