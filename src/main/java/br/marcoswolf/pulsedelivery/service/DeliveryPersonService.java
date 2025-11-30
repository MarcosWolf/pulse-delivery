package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.DeliveryPersonDTO;
import br.marcoswolf.pulsedelivery.mapper.DeliveryPersonMapper;
import br.marcoswolf.pulsedelivery.model.DeliveryPerson;
import br.marcoswolf.pulsedelivery.repository.DeliveryPersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryPersonService {
    private final DeliveryPersonRepository repository;;
    private final DeliveryPersonMapper mapper;

    public DeliveryPersonService(DeliveryPersonRepository repository, DeliveryPersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public DeliveryPerson createDeliveryPerson(DeliveryPersonDTO deliveryPersonDTO) {
        if (deliveryPersonDTO == null) {
            throw new IllegalArgumentException("Delivery Person cannot be null");
        }

        DeliveryPerson deliveryPerson = mapper.toEntity(deliveryPersonDTO);
        return repository.save(deliveryPerson);
    }

    @Transactional
    public DeliveryPerson updateDeliveryPerson(Long id, DeliveryPersonDTO updatedDTO) {
        DeliveryPerson existingDeliveryPerson = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Delivery Person not found"));

        mapper.updateDeliveryPersonFromDTO(updatedDTO, existingDeliveryPerson);

        return repository.save(existingDeliveryPerson);
    }

    public List<DeliveryPerson> getAllDeliveryPersons() {
        return repository.findAll();
    }

    public Optional<DeliveryPerson> getDeliveryPersonById(Long id) {
        return repository.findById(id);
    }
}
