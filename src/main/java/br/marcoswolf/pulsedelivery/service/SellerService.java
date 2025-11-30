package br.marcoswolf.pulsedelivery.service;

import br.marcoswolf.pulsedelivery.dto.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.SellerDTO;
import br.marcoswolf.pulsedelivery.mapper.AddressMapper;
import br.marcoswolf.pulsedelivery.mapper.SellerMapper;
import br.marcoswolf.pulsedelivery.mapper.SellerUpdateDTO;
import br.marcoswolf.pulsedelivery.model.Address;
import br.marcoswolf.pulsedelivery.model.Seller;
import br.marcoswolf.pulsedelivery.repository.SellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    private final SellerRepository repository;
    private final SellerMapper mapper;
    private final AddressMapper addressMapper;

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
