package br.marcoswolf.pulsedelivery.mapper;

import br.marcoswolf.pulsedelivery.dto.seller.SellerDTO;
import br.marcoswolf.pulsedelivery.dto.seller.SellerUpdateDTO;
import br.marcoswolf.pulsedelivery.model.Seller;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface SellerMapper {
    SellerDTO toDTO(Seller seller);
    Seller toEntity(SellerDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "image", ignore = true)
    void updateSellerFromDTO(SellerUpdateDTO dto, @MappingTarget Seller entity);
}
