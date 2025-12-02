package br.marcoswolf.pulsedelivery.repository;

import br.marcoswolf.pulsedelivery.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
