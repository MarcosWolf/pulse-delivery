package br.marcoswolf.pulsedelivery.repository;

import br.marcoswolf.pulsedelivery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
