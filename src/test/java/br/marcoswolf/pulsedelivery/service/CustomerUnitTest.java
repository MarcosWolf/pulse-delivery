package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.address.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.customer.CustomerDTO;
import br.marcoswolf.pulsedelivery.dto.customer.CustomerUpdateDTO;
import br.marcoswolf.pulsedelivery.mapper.AddressMapper;
import br.marcoswolf.pulsedelivery.mapper.CustomerMapper;
import br.marcoswolf.pulsedelivery.model.Address;
import br.marcoswolf.pulsedelivery.model.Customer;
import br.marcoswolf.pulsedelivery.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerUnitTest {
//    @Mock
//    private CustomerRepository repository;
//
//    @Mock
//    private CustomerMapper mapper;
//
//    @Mock
//    private AddressMapper addressMapper;
//
//    @InjectMocks
//    private CustomerService service;
//
//    @Test
//    void shouldCreateCustomerSuccessfully() {
//        AddressDTO addressDTO = new AddressDTO(
//                "Rua Lobo", "123", null, null, "Cidade X", "São Paulo", "00000-000", "Brasil"
//        );
//        CustomerDTO customerDTO = new CustomerDTO(
//                null, "Cliente X", "cliente@example.com", addressDTO
//        );
//
//        Address address = new Address();
//        address.setStreet("Rua Lobo");
//        address.setNumber("123");
//        address.setCity("Cidade X");
//        address.setState("São Paulo");
//        address.setPostalCode("00000-000");
//        address.setCountry("Brasil");
//
//        Customer customer = new Customer();
//        customer.setName("Marcos Vinícios");
//        customer.setEmail("viniciosramos.dev@gmail.com");
//        customer.setAddress(address);
//
//        when(mapper.toEntity(customerDTO)).thenReturn(customer);
//
//        Customer savedEntity = new Customer();
//        savedEntity.setId(1L);
//        savedEntity.setName("Marcos Vinícios");
//        savedEntity.setEmail("viniciosramos.dev@gmail.com");
//        savedEntity.setAddress(address);
//
//        when(repository.save(any(Customer.class))).thenReturn(savedEntity);
//
//        Customer result = service.createCustomer(customerDTO);
//
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        assertEquals("Marcos Vinícios", result.getName());
//        assertEquals("viniciosramos.dev@gmail.com", result.getEmail());
//
//        verify(mapper).toEntity(customerDTO);
//        verify(repository).save(any(Customer.class));
//    }
//
//    void shouldUpdateBasicInfoSuccessfully() {
//        Customer existingCustomer = new Customer(
//                1L,
//                "Marcos Vinícios",
//                "viniciosramos.dev@gmail.com",
//                new Address("Rua Lobo", "123", null, null, "Cidade X", "SP", "00000-000", "Brasil")
//        );
//
//        when(repository.findById(1L)).thenReturn(Optional.of(existingCustomer));
//
//        CustomerUpdateDTO updateDTO = new CustomerUpdateDTO(
//                1L,
//                "João Silva",
//                "joao@gmail.com"
//        );
//
//        doAnswer(invocation -> {
//            Customer customer = invocation.getArgument(1);
//            customer.setName("João Silva");
//            customer.setEmail("joao@gmail.com");
//            return null;
//        }).when(mapper).updateCustomerFromDTO(updateDTO, existingCustomer);
//
//        when(repository.save(any(Customer.class))).thenReturn(existingCustomer);
//
//        Customer result = service.updateBasicInfo(1L, updateDTO);
//
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        assertEquals("João Silva", result.getName());
//        assertEquals("joao@gmail.com", result.getEmail());
//        assertEquals("Rua Lobo", result.getAddress().getStreet());  // Address mantido
//
//        verify(repository).findById(1L);
//        verify(mapper).updateCustomerFromDTO(updateDTO, existingCustomer);
//        verify(repository).save(existingCustomer);
//    }
//
//    @Test
//    void shouldUpdateAddressSuccessfully() {
//        Customer existingCustomer = new Customer(
//                1L,
//                "Marcos Vinícios",
//                "viniciosramos.dev@gmail.com",
//                new Address("Rua Lobo", "123", null, null, "Cidade X", "SP", "00000-000", "Brasil")
//        );
//
//        when(repository.findById(1L)).thenReturn(Optional.of(existingCustomer));
//
//        AddressDTO newAddressDTO = new AddressDTO(
//                "Avenida Paulista", "1000", "Apto 501", "Bela Vista",
//                "São Paulo", "SP", "01310-100", "Brasil"
//        );
//
//        Address newAddress = new Address(
//                "Avenida Paulista", "1000", "Apto 501", "Bela Vista",
//                "São Paulo", "SP", "01310-100", "Brasil"
//        );
//
//        when(addressMapper.toEntity(newAddressDTO)).thenReturn(newAddress);
//        when(repository.save(any(Customer.class))).thenReturn(existingCustomer);
//
//        Customer result = service.updateAddress(1L, newAddressDTO);
//
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        assertEquals("Marcos Vinícios", result.getName());  // Nome mantido
//        assertEquals("viniciosramos.dev@gmail.com", result.getEmail());  // Email mantido
//        assertEquals("Avenida Paulista", result.getAddress().getStreet());  // Address atualizado
//        assertEquals("1000", result.getAddress().getNumber());
//
//        verify(repository).findById(1L);
//        verify(addressMapper).toEntity(newAddressDTO);
//        verify(repository).save(existingCustomer);
//    }
//
//    @Test
//    void shouldThrowExceptionDtoIsNull() {
//        assertThrows(IllegalArgumentException.class, () -> service.createCustomer(null));
//        verifyNoInteractions(repository, mapper);
//    }
//
//    @Test
//    void shouldGetCustomerByIdSuccessfully() {
//        Customer customer = new Customer();
//        customer.setId(1L);
//        when(repository.findById(1L)).thenReturn(Optional.of(customer));
//
//        Optional<Customer> result = service.getCustomerById(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals(1L, result.get().getId());
//        verify(repository).findById(1L);
//    }
//
//    @Test
//    void shouldReturnEmptyWhenCustomerNotFound() {
//        when(repository.findById(2L)).thenReturn(Optional.empty());
//
//        Optional<Customer> result = service.getCustomerById(2L);
//
//        assertTrue(result.isEmpty());
//        verify(repository).findById(2L);
//    }
}