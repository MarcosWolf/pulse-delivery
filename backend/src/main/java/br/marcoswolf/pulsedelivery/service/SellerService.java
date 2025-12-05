package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.address.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.seller.SellerDTO;
import br.marcoswolf.pulsedelivery.mapper.AddressMapper;
import br.marcoswolf.pulsedelivery.mapper.SellerMapper;
import br.marcoswolf.pulsedelivery.dto.seller.SellerUpdateDTO;
import br.marcoswolf.pulsedelivery.model.Address;
import br.marcoswolf.pulsedelivery.model.Seller;
import br.marcoswolf.pulsedelivery.repository.SellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SellerService {
    private final SellerRepository repository;
    private final SellerMapper mapper;
    private final AddressMapper addressMapper;

    public static final String UPLOAD_PATH = "uploads/sellers";

    public SellerService(SellerRepository repository, SellerMapper mapper, AddressMapper addressMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.addressMapper = addressMapper;
    }

    @Transactional
    public Seller createSeller(SellerDTO sellerDTO) {
        if (sellerDTO == null) {
            throw new IllegalArgumentException("Seller cannot be null");
        }

        Seller seller = mapper.toEntity(sellerDTO);
        return repository.save(seller);
    }

    @Transactional
    public Seller updateImage(Long id, MultipartFile file) {
        Seller seller = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("Seller not found")));

        if (file == null | file.isEmpty()) {
            throw new IllegalArgumentException("Empty file");
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_PATH);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalName = file.getOriginalFilename();
            String extension = originalName != null && originalName.contains(".")
                    ? originalName.substring(originalName.lastIndexOf("."))
                    : "";

            String newFileName = UUID.randomUUID() + extension;

            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath);

            String publicUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/" + UPLOAD_PATH + "/")
                    .path(newFileName)
                    .toUriString();

            seller.setStoreImageUrl(publicUrl);

            return repository.save(seller);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file", e);
        }
    }

    @Transactional
    public Seller updateAddress(Long id, AddressDTO updatedDTO) {
        Seller existingSeller = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("Seller not found")));

        Address newAddress = addressMapper.toEntity(updatedDTO);
        existingSeller.setAddress(newAddress);

        return repository.save(existingSeller);
    }

    @Transactional
    public Seller updateBasicInfo(Long id, SellerUpdateDTO updatedDTO) {
        Seller existingSeller = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("Seller not found")));

        mapper.updateSellerFromDTO(updatedDTO, existingSeller);

        return repository.save(existingSeller);
    }

    public List<Seller> getAllSellers() {
       return repository.findAll();
    }

    public Optional<Seller> getSellerById(Long id) {
        return repository.findById(id);
    }
}
