package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.CustomerDTO;
import br.marcoswolf.pulsedelivery.dto.CustomerUpdateDTO;
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
    void shouldUpdateBasicInfoSuccessfully() {
        CustomerDTO customerDTO = createCustomerDTO();
        Customer savedCustomer = service.createCustomer(customerDTO);

        CustomerUpdateDTO dtoToUpdate = new CustomerUpdateDTO(
                savedCustomer.getId(),
                "João Silva",
                "joao@gmail.com"
        );

        Customer updated = service.updateBasicInfo(savedCustomer.getId(), dtoToUpdate);

        assertNotNull(updated);
        assertEquals(savedCustomer.getId(), updated.getId());
        assertEquals("João Silva", updated.getName());
        assertEquals("joao@gmail.com", updated.getEmail());

        assertEquals("Rua Lobo", updated.getAddress().getStreet());
        assertEquals("123", updated.getAddress().getNumber());

        Optional<Customer> foundCustomer = repository.findById(updated.getId());
        assertTrue(foundCustomer.isPresent());
        assertEquals("João Silva", foundCustomer.get().getName());
        assertEquals("joao@gmail.com", foundCustomer.get().getEmail());
        assertEquals("Rua Lobo", foundCustomer.get().getAddress().getStreet());
    }

    @Test
    void shouldUpdateAddressSuccessfully() {
        CustomerDTO customerDTO = createCustomerDTO();
        Customer savedCustomer = service.createCustomer(customerDTO);

        AddressDTO newAddress = new AddressDTO(
                "Avenida Paulista",
                "1000",
                "Apto 501",
                "Bela Vista",
                "São Paulo",
                "SP",
                "01310-100",
                "Brasil"
        );

        Customer updated = service.updateAddress(savedCustomer.getId(), newAddress);

        assertNotNull(updated);
        assertEquals(savedCustomer.getId(), updated.getId());

        assertEquals("Marcos Vinícios", updated.getName());
        assertEquals("viniciosramos.dev@gmail.com", updated.getEmail());

        assertEquals("Avenida Paulista", updated.getAddress().getStreet());
        assertEquals("1000", updated.getAddress().getNumber());
        assertEquals("Apto 501", updated.getAddress().getComplement());
        assertEquals("Bela Vista", updated.getAddress().getNeighborhood());

        Optional<Customer> foundCustomer = repository.findById(updated.getId());
        assertTrue(foundCustomer.isPresent());
        assertEquals("Avenida Paulista", foundCustomer.get().getAddress().getStreet());
        assertEquals("1000", foundCustomer.get().getAddress().getNumber());
    }

    @Test
    void shouldUpdateBothBasicInfoAndAddressSeparately() {
        CustomerDTO customerDTO = createCustomerDTO();
        Customer savedCustomer = service.createCustomer(customerDTO);

        CustomerUpdateDTO basicInfoUpdate = new CustomerUpdateDTO(
                savedCustomer.getId(),
                "João Silva",
                "joao@gmail.com"
        );
        Customer afterBasicUpdate = service.updateBasicInfo(savedCustomer.getId(), basicInfoUpdate);

        assertEquals("João Silva", afterBasicUpdate.getName());
        assertEquals("joao@gmail.com", afterBasicUpdate.getEmail());
        assertEquals("Rua Lobo", afterBasicUpdate.getAddress().getStreet());

        AddressDTO newAddress = new AddressDTO(
                "Rua Nova",
                "999",
                null,
                null,
                "Rio de Janeiro",
                "RJ",
                "20000-000",
                "Brasil"
        );
        Customer afterAddressUpdate = service.updateAddress(savedCustomer.getId(), newAddress);

        assertEquals("João Silva", afterAddressUpdate.getName());
        assertEquals("joao@gmail.com", afterAddressUpdate.getEmail());
        assertEquals("Rua Nova", afterAddressUpdate.getAddress().getStreet());
        assertEquals("999", afterAddressUpdate.getAddress().getNumber());
        assertEquals("Rio de Janeiro", afterAddressUpdate.getAddress().getCity());
    }


    private AddressDTO createAddressDTO() {
        return new AddressDTO(
                "Rua Lobo",
                "123",
                null,
                "Bela Vista",
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

    private CustomerUpdateDTO createCustomerUpdateDTO() {
        return new CustomerUpdateDTO(
                null,
                "Marcos Vinícios",
                "viniciosramos.dev@gmail.com"
        );
    }
}