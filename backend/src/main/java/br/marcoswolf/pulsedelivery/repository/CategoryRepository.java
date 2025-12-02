package br.marcoswolf.pulsedelivery.repository;

import br.marcoswolf.pulsedelivery.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
