package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.CustomerDTO;
import br.marcoswolf.pulsedelivery.mapper.CustomerMapper;
import br.marcoswolf.pulsedelivery.model.Address;
import br.marcoswolf.pulsedelivery.model.Customer;
import br.marcoswolf.pulsedelivery.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerUnitTest {
    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerService service;

    @Test
    void shouldCreateCustomerSuccessfully() {
        AddressDTO addressDTO = new AddressDTO(
                "Rua Lobo", "123", null, null, "Cidade X", "São Paulo", "00000-000", "Brasil"
        );
        CustomerDTO customerDTO = new CustomerDTO(
                null, "Cliente X", "cliente@example.com", addressDTO
        );

        Address address = new Address();
        address.setStreet("Rua Lobo");
        address.setNumber("123");
        address.setCity("Cidade X");
        address.setState("São Paulo");
        address.setPostalCode("00000-000");
        address.setCountry("Brasil");

        Customer customer = new Customer();
        customer.setName("Marcos Vinícios");
        customer.setEmail("viniciosramos.dev@gmail.com");
        customer.setAddress(address);

        when(mapper.toEntity(customerDTO)).thenReturn(customer);

        Customer savedEntity = new Customer();
        savedEntity.setId(1L);
        savedEntity.setName("Marcos Vinícios");
        savedEntity.setEmail("viniciosramos.dev@gmail.com");
        savedEntity.setAddress(address);

        when(repository.save(any(Customer.class))).thenReturn(savedEntity);

        Customer result = service.createCustomer(customerDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Marcos Vinícios", result.getName());
        assertEquals("viniciosramos.dev@gmail.com", result.getEmail());

        verify(mapper).toEntity(customerDTO);
        verify(repository).save(any(Customer.class));
    }

    @Test
    void shouldUpdateCustomerSuccessfully() {
        Address address = new Address();
        address.setStreet("Rua Lobo");
        address.setNumber("123");

        Address address2 = new Address();
        address2.setStreet("Rua X");
        address2.setNumber("456");

        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setName("Marcos Vinícios");
        existingCustomer.setEmail("viniciosramos.dev@gmail.com");
        existingCustomer.setAddress(address);

        when(repository.findById(1L)).thenReturn(Optional.of(existingCustomer));

        Customer customerToUpdate = new Customer();
        customerToUpdate.setId(1L);
        customerToUpdate.setName("Marcos Vinícios");
        customerToUpdate.setEmail("vinicios@gmail.com");
        customerToUpdate.setAddress(address2);

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1L);
        updatedCustomer.setName("Marcos Vinícios");
        updatedCustomer.setEmail("vinicios@gmail.com");
        updatedCustomer.setAddress(address2);

        when(repository.save(any(Customer.class))).thenReturn(updatedCustomer);

        Customer result = service.updateCustomer(1L, customerToUpdate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Marcos Vinícios", result.getName());
        assertEquals("vinicios@gmail.com", result.getEmail());
        assertEquals("Rua X", result.getAddress().getStreet());
        assertEquals("456", result.getAddress().getNumber());

        verify(repository).findById(1L);
        verify(repository).save(any(Customer.class));
    }

    @Test
    void shouldThrowExceptionDtoIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.createCustomer(null));
        verifyNoInteractions(repository, mapper);
    }

    @Test
    void shouldGetCustomerByIdSuccessfully() {
        Customer customer = new Customer();
        customer.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> result = service.getCustomerById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(repository).findById(1L);
    }

    @Test
    void shouldReturnEmptyWhenCustomerNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<Customer> result = service.getCustomerById(2L);

        assertTrue(result.isEmpty());
        verify(repository).findById(2L);
    }
}