package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.ProductDTO;
import br.marcoswolf.pulsedelivery.mapper.ProductMapper;
import br.marcoswolf.pulsedelivery.model.Product;
import br.marcoswolf.pulsedelivery.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    public ProductController(ProductService service, ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = service.createProduct(productDTO);
        ProductDTO dto = mapper.toDTO(product);
        return ResponseEntity
                .created(URI.create("/products/" + product.getId()))
                .body(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product savedProduct = service.updateProduct(id, productDTO);
        return ResponseEntity.ok(mapper.toDTO(savedProduct));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<ProductDTO> toggleActive(@PathVariable Long id) {
        Product product = service.toggleActive(id);
        return ResponseEntity.ok(mapper.toDTO(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(@RequestParam(required = false) Long categoryId, @RequestParam(required = false) Boolean active) {
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
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return service.getProductById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
