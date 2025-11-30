package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.CategoryDTO;
import br.marcoswolf.pulsedelivery.mapper.CategoryMapper;
import br.marcoswolf.pulsedelivery.model.Category;
import br.marcoswolf.pulsedelivery.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Manages product categories")
public class CategoryController {
    private final CategoryService service;
    private final CategoryMapper mapper;

    public CategoryController(CategoryService service, CategoryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Creates a new category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Snacks\",\"active\":true}")
                    )),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CategoryDTO> createCategory(
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            @RequestBody(
                    description = "DTO with category data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class),
                            examples = @ExampleObject(value = "{\"name\":\"Snacks\",\"active\":true}")
                    )
            )
            CategoryDTO categoryDTO) {

        Category category = service.createCategory(categoryDTO);
        CategoryDTO dto = mapper.toDTO(category);
        return ResponseEntity
                .created(URI.create("/categories/" + category.getId()))
                .body(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Updates an existing category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Drinks\",\"active\":true}")
                    )),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            @RequestBody(
                    description = "DTO with fields to update (name or active)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class),
                            examples = @ExampleObject(value = "{\"name\":\"Drinks\"}")
                    )
            )
            CategoryDTO categoryDTO) {

        Category savedCategory = service.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(mapper.toDTO(savedCategory));
    }

    @PatchMapping("/{id}/active")
    @Operation(summary = "Toggles a category's active status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Snacks\",\"active\":false}")
                    )),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDTO> toggleActive(@PathVariable Long id) {
        Category updated = service.toggleActive(id);
        return ResponseEntity.ok(mapper.toDTO(updated));
    }

    @GetMapping
    @Operation(summary = "Returns all categories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of categories",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class)
                    ))
    })
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = service.getAllCategories();
        List<CategoryDTO> dtos = categories.stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a category by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Snacks\",\"active\":true}")
                    )),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return service.getCategoryById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}