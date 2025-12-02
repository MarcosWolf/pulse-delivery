package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.product.ProductDTO;
import br.marcoswolf.pulsedelivery.mapper.ProductMapper;
import br.marcoswolf.pulsedelivery.model.Product;
import br.marcoswolf.pulsedelivery.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Product createProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        Product product = mapper.toEntity(productDTO);

        return repository.save(product);
    }

    public Product updateProduct(Long id, ProductDTO updatedDTO) {
        Product existingProduct = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("Product not found")));

        mapper.updateProductFromDTO(updatedDTO, existingProduct);

        return repository.save(existingProduct);
    }

    @Transactional
    public Product toggleActive(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setActive(!product.getActive());

        return repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    public List<Product> getActiveProducts() {
        return repository.findByActive(true);
    }
}
