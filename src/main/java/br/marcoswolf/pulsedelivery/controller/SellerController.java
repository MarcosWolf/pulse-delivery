package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.address.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.seller.SellerDTO;
import br.marcoswolf.pulsedelivery.mapper.SellerMapper;
import br.marcoswolf.pulsedelivery.dto.seller.SellerUpdateDTO;
import br.marcoswolf.pulsedelivery.model.Seller;
import br.marcoswolf.pulsedelivery.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/sellers")
@Tag(name = "Sellers", description = "Manage sellers")
public class SellerController {
    private final SellerService service;
    private final SellerMapper mapper;

    public SellerController(SellerService service, SellerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Creates a new seller")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Seller successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SellerDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Acme Corp\",\"email\":\"acme@example.com\"}")
                    )),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<SellerDTO> createSeller(
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO containing seller details",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SellerDTO.class),
                            examples = @ExampleObject(value = "{\"name\":\"Acme Corp\",\"email\":\"acme@example.com\",\"address\":{\"street\":\"Main St\",\"number\":\"123\",\"city\":\"Metropolis\",\"state\":\"NY\",\"zip\":\"10001\",\"country\":\"USA\"}}")
                    )
            )
            SellerDTO sellerDTO) {

        Seller seller = service.createSeller(sellerDTO);
        SellerDTO dto = mapper.toDTO(seller);
        return ResponseEntity
                .created(URI.create("/sellers/" + seller.getId()))
                .body(dto);
    }

    @PatchMapping("/{id}/address")
    @Operation(summary = "Updates seller's address")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SellerDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Acme Corp\",\"address\":{\"street\":\"Main St\",\"number\":\"456\",\"complement\":\"Apt 123\",\"neighborhood\":\"Centro\",\"city\":\"Metropolis\",\"state\":\"NY\",\"postalCode\":\"10001\",\"country\":\"USA\"}}")
                    )),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    public ResponseEntity<SellerDTO> updateAddress(
            @PathVariable Long id,
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO containing the new address",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AddressDTO.class),
                            examples = @ExampleObject(value = "{\"street\":\"Main St\",\"number\":\"456\",\"complement\":\"Apt 123\",\"neighborhood\":\"Centro\",\"city\":\"Metropolis\",\"state\":\"NY\",\"zip\":\"10001\",\"country\":\"USA\"}")
                    )
            )
            AddressDTO dto) {

        Seller updated = service.updateAddress(id, dto);
        return ResponseEntity.ok(mapper.toDTO(updated));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Updates seller's basic information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seller successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SellerDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Acme Corp\",\"email\":\"acme@example.com\"}")
                    )),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    public ResponseEntity<SellerDTO> updateBasicInfo(
            @PathVariable Long id,
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO containing fields to update",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SellerUpdateDTO.class),
                            examples = @ExampleObject(value = "{\"name\":\"Acme Corp\",\"email\":\"acme@example.com\"}")
                    )
            )
            SellerUpdateDTO sellerDTO) {

        Seller savedSeller = service.updateBasicInfo(id, sellerDTO);
        return ResponseEntity.ok(mapper.toDTO(savedSeller));
    }

    @GetMapping
    @Operation(summary = "Returns all sellers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of sellers",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SellerDTO.class)
                    ))
    })
    public ResponseEntity<List<SellerDTO>> getAllSellers() {
        List<Seller> sellers = service.getAllSellers();
        List<SellerDTO> dtos = sellers.stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a seller by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seller found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SellerDTO.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Acme Corp\",\"email\":\"acme@example.com\",\"address\":{\"street\":\"Main St\",\"number\":\"123\",\"complement\":\"Apt 123\",\"neighborhood\":\"Centro\",\"city\":\"Metropolis\",\"state\":\"NY\",\"zip\":\"10001\",\"country\":\"USA\"}}")
                    )),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    public ResponseEntity<SellerDTO> getSellerById(@PathVariable Long id) {
        return service.getSellerById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}