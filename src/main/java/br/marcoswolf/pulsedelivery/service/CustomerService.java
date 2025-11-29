package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.CustomerDTO;
import br.marcoswolf.pulsedelivery.dto.CustomerUpdateDTO;
import br.marcoswolf.pulsedelivery.mapper.AddressMapper;
import br.marcoswolf.pulsedelivery.mapper.CustomerMapper;
import br.marcoswolf.pulsedelivery.model.Address;
import br.marcoswolf.pulsedelivery.model.Customer;
import br.marcoswolf.pulsedelivery.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    private final AddressMapper addressMapper;

    public CustomerService(CustomerRepository repository, CustomerMapper mapper, AddressMapper addressMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.addressMapper = addressMapper;
    }

    public Customer createCustomer(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        Customer customer = mapper.toEntity(customerDTO);
        return repository.save(customer);
    }

    @Transactional
    public Customer updateAddress(Long id, AddressDTO updatedDTO) {
        Customer existingCustomer = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Address newAddress = addressMapper.toEntity(updatedDTO);
        existingCustomer.setAddress(newAddress);

        return repository.save(existingCustomer);
    }

    @Transactional
    public Customer updateBasicInfo(Long id, CustomerUpdateDTO updatedDTO) {
        Customer existingCustomer = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("Customer not found")));

        mapper.updateCustomerFromDTO(updatedDTO, existingCustomer);

        return repository.save(existingCustomer);
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return repository.findById(id);
    }
}
