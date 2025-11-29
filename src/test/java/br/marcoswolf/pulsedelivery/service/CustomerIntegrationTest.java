package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.CustomerDTO;
import br.marcoswolf.pulsedelivery.model.Customer;
import br.marcoswolf.pulsedelivery.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerIntegrationTest {
    @Autowired
    private CustomerService service;

    @Autowired
    private CustomerRepository repository;

    @Test
    void shouldSaveAndReturnCustomer() {
        CustomerDTO customerDTO = createCustomerDTO();

        Customer savedCustomer = service.createCustomer(customerDTO);

        assertNotNull(savedCustomer.getId());
        assertNotNull(savedCustomer.getAddress());
        assertEquals("Marcos Vinícios", savedCustomer.getName());
        assertEquals("viniciosramos.dev@gmail.com", savedCustomer.getEmail());

        Optional<Customer> foundCustomer = service.getCustomerById(savedCustomer.getId());

        assertTrue(foundCustomer.isPresent());
        assertEquals("Rua Lobo", foundCustomer.get().getAddress().getStreet());
        assertEquals("123", foundCustomer.get().getAddress().getNumber());
    }

    @Test
    void shouldUpdateCustomerSucessfully() {
        CustomerDTO customerDTO = createCustomerDTO();

        Customer savedCustomer = service.createCustomer(customerDTO);

        Customer customerToUpdate = new Customer();
        customerToUpdate.setId(savedCustomer.getId());
        customerToUpdate.setName(savedCustomer.getName());
        customerToUpdate.setEmail("vinicios@gmail.com");
        customerToUpdate.setAddress(savedCustomer.getAddress());

        Customer updated = service.updateCustomer(savedCustomer.getId(), customerToUpdate);

        assertNotNull(updated);
        assertEquals(savedCustomer.getId(), updated.getId());
        assertEquals("vinicios@gmail.com", updated.getEmail());

        Optional<Customer> foundCustomer = repository.findById(updated.getId());
        assertTrue(foundCustomer.isPresent());
        assertEquals("vinicios@gmail.com", foundCustomer.get().getEmail());
    }

    private AddressDTO createAddressDTO() {
        return new AddressDTO(
                "Rua Lobo",
                "123",
                null,
                null,
                "Cidade X",
                "São Paulo",
                "00000-000",
                "Brasil"
        );
    }

    private CustomerDTO createCustomerDTO() {
        return new CustomerDTO(
                null,
                "Marcos Vinícios",
                "viniciosramos.dev@gmail.com",
                createAddressDTO()
        );
    }
}