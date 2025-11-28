package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.CustomerDTO;
import br.marcoswolf.pulsedelivery.mapper.CustomerMapper;
import br.marcoswolf.pulsedelivery.model.Customer;
import br.marcoswolf.pulsedelivery.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Customer createCustomer(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        Customer customer = mapper.toEntity(customerDTO);
        return repository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updated) {
        Customer existingCustomer = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("Customer not found")));

        existingCustomer.setName(updated.getName());
        existingCustomer.setEmail(updated.getEmail());
        existingCustomer.setAddress(updated.getAddress());

        return repository.save(existingCustomer);
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return repository.findById(id);
    }
}
