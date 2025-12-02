package br.marcoswolf.pulsedelivery.repository;

import br.marcoswolf.pulsedelivery.model.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {
}
