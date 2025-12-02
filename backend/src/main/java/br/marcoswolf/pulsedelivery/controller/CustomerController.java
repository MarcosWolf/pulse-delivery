package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.address.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.customer.CustomerDTO;
import br.marcoswolf.pulsedelivery.dto.customer.CustomerUpdateDTO;
import br.marcoswolf.pulsedelivery.mapper.CustomerMapper;
import br.marcoswolf.pulsedelivery.model.Customer;
import br.marcoswolf.pulsedelivery.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customers", description = "Manages customers")
public class CustomerController {
    private final CustomerService service;
    private final CustomerMapper mapper;

    public CustomerController(CustomerService service, CustomerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Creates a new customer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"John Doe\",\"email\":\"john@example.com\"}")
                    )),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CustomerDTO> createCustomer(
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            @RequestBody(
                    description = "DTO containing customer details",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class),
                            examples = @ExampleObject(value = "{\"name\":\"John Doe\",\"email\":\"john@example.com\",\"address\":{\"street\":\"Main St\",\"number\":\"123\",\"complement\":\"Apt 123\",\"neighborhood\":\"Centro\",\"city\":\"Springfield\",\"state\":\"IL\",\"zip\":\"62701\",\"country\":\"USA\"}}")
                    )
            )
            CustomerDTO customerDTO) {

        Customer customer = service.createCustomer(customerDTO);
        CustomerDTO dto = mapper.toDTO(customer);
        return ResponseEntity
                .created(URI.create("/customers/" + customer.getId()))
                .body(dto);
    }

    @PatchMapping("/{id}/address")
    @Operation(summary = "Updates customer's address")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"John Doe\",\"address\":{\"street\":\"Main St\",\"number\":\"456\",\"complement\":\"Apt 123\",\"neighborhood\":\"Centro\",\"city\":\"Springfield\",\"state\":\"IL\",\"postalCode\":\"62701\",\"country\":\"USA\"}}")
                    )),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<CustomerDTO> updateAddress(
            @PathVariable Long id,
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            @RequestBody(
                    description = "DTO containing the new address",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AddressDTO.class),
                            examples = @ExampleObject(value = "{\"street\":\"Main St\",\"number\":\"456\",\"complement\":\"Apt 123\",\"neighborhood\":\"Centro\",\"city\":\"Springfield\",\"state\":\"IL\",\"postalCode\":\"62701\",\"country\":\"USA\"}")
                    )
            )
            AddressDTO dto) {

        Customer updated = service.updateAddress(id, dto);
        return ResponseEntity.ok(mapper.toDTO(updated));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Updates customer's basic information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Jane Doe\",\"email\":\"jane@example.com\"}")
                    )),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<CustomerDTO> updateBasicInfo(
            @PathVariable Long id,
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            @RequestBody(
                    description = "DTO containing fields to update",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerUpdateDTO.class),
                            examples = @ExampleObject(value = "{\"name\":\"Jane Doe\",\"email\":\"jane@example.com\"}")
                    )
            )
            CustomerUpdateDTO customerDTO) {

        Customer savedCustomer = service.updateBasicInfo(id, customerDTO);
        return ResponseEntity.ok(mapper.toDTO(savedCustomer));
    }

    @GetMapping
    @Operation(summary = "Returns all customers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of customers",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class)
                    ))
    })
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = service.getAllCustomers();
        List<CustomerDTO> dtos = customers.stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a customer by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"John Doe\",\"email\":\"john@example.com\",\"address\":{\"street\":\"Main St\",\"number\":\"123\",\"complement\":\"Apt 123\",\"neighborhood\":\"Centro\",\"city\":\"Springfield\",\"state\":\"IL\",\"postalCode\":\"62701\",\"country\":\"USA\"}}")
                    )),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return service.getCustomerById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}