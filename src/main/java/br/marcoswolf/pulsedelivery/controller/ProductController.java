package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.product.ProductDTO;
import br.marcoswolf.pulsedelivery.mapper.ProductMapper;
import br.marcoswolf.pulsedelivery.model.Product;
import br.marcoswolf.pulsedelivery.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Manages products")
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    public ProductController(ProductService service, ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Creates a new product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Burger\",\"price\":12.5,\"active\":true,\"categoryId\":1}")
                    )),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ProductDTO> createProduct(
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            @RequestBody(
                    description = "DTO containing product details",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(value = "{\"name\":\"Burger\",\"price\":12.5,\"active\":true,\"categoryId\":1}")
                    )
            )
            ProductDTO productDTO) {

        Product product = service.createProduct(productDTO);
        ProductDTO dto = mapper.toDTO(product);
        return ResponseEntity
                .created(URI.create("/products/" + product.getId()))
                .body(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Updates an existing product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Cheeseburger\",\"price\":13.0,\"active\":true,\"categoryId\":1}")
                    )),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            @RequestBody(
                    description = "DTO containing product fields to update",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(value = "{\"name\":\"Cheeseburger\",\"price\":13.0}")
                    )
            )
            ProductDTO productDTO) {

        Product savedProduct = service.updateProduct(id, productDTO);
        return ResponseEntity.ok(mapper.toDTO(savedProduct));
    }

    @PatchMapping("/{id}/active")
    @Operation(summary = "Activates or deactivates a product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product status updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Burger\",\"active\":false}")
                    )),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> toggleActive(@PathVariable Long id) {
        Product product = service.toggleActive(id);
        return ResponseEntity.ok(mapper.toDTO(product));
    }

    @GetMapping
    @Operation(summary = "Returns all products, optionally filtered by category or active status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of products",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)
                    ))
    })
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean active) {

        List<Product> products;

        if (categoryId != null) {
            products = service.getProductsByCategoryId(categoryId);
        } else if (active != null) {
            products = service.getActiveProducts();
        } else {
            products = service.getAllProducts();
        }

        List<ProductDTO> dtos = products.stream()
                .map(mapper::toDTO)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Burger\",\"price\":12.5,\"active\":true,\"categoryId\":1}")
                    )),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return service.getProductById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}