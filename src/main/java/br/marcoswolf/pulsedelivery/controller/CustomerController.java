package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.CustomerDTO;
import br.marcoswolf.pulsedelivery.dto.CustomerUpdateDTO;
import br.marcoswolf.pulsedelivery.mapper.CustomerMapper;
import br.marcoswolf.pulsedelivery.model.Customer;
import br.marcoswolf.pulsedelivery.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService service;
    private final CustomerMapper mapper;

    public CustomerController(CustomerService service, CustomerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = service.createCustomer(customerDTO);
        CustomerDTO dto = mapper.toDTO(customer);
        return ResponseEntity
                .created(URI.create("/customers/" + customer.getId()))
                .body(dto);
    }

    @PatchMapping("/{id}/address")
    public ResponseEntity<CustomerDTO> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressDTO dto) {
        Customer updated = service.updateAddress(id, dto);
        return ResponseEntity.ok(mapper.toDTO(updated));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateBasicInfo(@PathVariable Long id, @Valid @RequestBody CustomerUpdateDTO customerDTO) {
        Customer savedCustomer = service.updateBasicInfo(id, customerDTO);
        return ResponseEntity.ok(mapper.toDTO(savedCustomer));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = service.getAllCustomers();
        List<CustomerDTO> dtos = customers.stream()
                                    .map(mapper::toDTO)
                                    .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return service.getCustomerById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
