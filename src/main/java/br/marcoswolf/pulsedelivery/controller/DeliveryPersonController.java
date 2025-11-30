package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.DeliveryPersonDTO;
import br.marcoswolf.pulsedelivery.mapper.DeliveryPersonMapper;
import br.marcoswolf.pulsedelivery.model.DeliveryPerson;
import br.marcoswolf.pulsedelivery.service.DeliveryPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/delivery-persons")
@Tag(name = "Delivery Persons", description = "Manages delivery persons")
public class DeliveryPersonController {
    private final DeliveryPersonService service;
    private final DeliveryPersonMapper mapper;

    public DeliveryPersonController(DeliveryPersonService service, DeliveryPersonMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Creates a new delivery person")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Delivery person successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryPersonDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"John Doe\",\"phone\":\"+123456789\",\"document\":\"123.456.789-00\",\"active\":true}")
                    )),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<DeliveryPersonDTO> createDeliveryPerson(
            @Valid
            @RequestBody(
                    description = "DTO containing delivery person details",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryPersonDTO.class),
                            examples = @ExampleObject(value = "{\"name\":\"John Doe\",\"phone\":\"+123456789\",\"document\":\"123.456.789-00\",\"active\":true}")
                    )
            )
            DeliveryPersonDTO deliveryPersonDTO) {

        DeliveryPerson deliveryPerson = service.createDeliveryPerson(deliveryPersonDTO);
        DeliveryPersonDTO dto = mapper.toDTO(deliveryPerson);
        return ResponseEntity
                .created(URI.create("/delivery-persons/" + deliveryPerson.getId()))
                .body(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a delivery person's details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delivery person successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryPersonDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"John Doe\",\"phone\":\"+123456789\",\"document\":\"123.456.789-00\",\"active\":true}")
                    )),
            @ApiResponse(responseCode = "404", description = "Delivery person not found")
    })
    public ResponseEntity<DeliveryPersonDTO> updateDeliveryPerson(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryPersonDTO deliveryPersonDTO) {
        DeliveryPerson savedDeliveryPerson = service.updateDeliveryPerson(id, deliveryPersonDTO);
        return ResponseEntity.ok(mapper.toDTO(savedDeliveryPerson));
    }

    @GetMapping
    @Operation(summary = "Returns all delivery persons")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of delivery persons",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryPersonDTO.class)
                    ))
    })
    public ResponseEntity<List<DeliveryPersonDTO>> getAllDeliveryPersons() {
        List<DeliveryPerson> deliveryPersons = service.getAllDeliveryPersons();
        List<DeliveryPersonDTO> dtos = deliveryPersons.stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a delivery person by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delivery person found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryPersonDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"John Doe\",\"phone\":\"+123456789\",\"document\":\"123.456.789-00\",\"active\":true}")
                    )),
            @ApiResponse(responseCode = "404", description = "Delivery person not found")
    })
    public ResponseEntity<DeliveryPersonDTO> getDeliveryPersonById(@PathVariable Long id) {
        return service.getDeliveryPersonById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}