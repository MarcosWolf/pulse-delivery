package br.marcoswolf.pulsedelivery.controller;

import br.marcoswolf.pulsedelivery.dto.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.SellerDTO;
import br.marcoswolf.pulsedelivery.mapper.SellerMapper;
import br.marcoswolf.pulsedelivery.mapper.SellerUpdateDTO;
import br.marcoswolf.pulsedelivery.model.Seller;
import br.marcoswolf.pulsedelivery.service.SellerService;
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
    public ResponseEntity<SellerDTO> createSeller(@Valid @RequestBody SellerDTO sellerDTO) {
        Seller seller = service.createSeller(sellerDTO);
        SellerDTO dto = mapper.toDTO(seller);
        return ResponseEntity
                .created(URI.create("/sellers/" + seller.getId()))
                .body(dto);
    }

    @PatchMapping("/{id}/address")
    public ResponseEntity<SellerDTO> updateAddress(@PathVariable Long id, @Valid @RequestBody AddressDTO dto) {
        Seller updated = service.updateAddress(id, dto);
        return ResponseEntity.ok(mapper.toDTO(updated));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SellerDTO> updateBasicInfo(@PathVariable Long id, @Valid @RequestBody SellerUpdateDTO sellerDTO) {
        Seller savedSeller = service.updateBasicInfo(id, sellerDTO);
        return ResponseEntity.ok(mapper.toDTO(savedSeller));
    }

    @GetMapping
    public ResponseEntity<List<SellerDTO>> getAllSellers() {
        List<Seller> sellers = service.getAllSellers();
        List<SellerDTO> dtos = sellers.stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerDTO> getSellerById(@PathVariable Long id) {
        return service.getSellerById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
