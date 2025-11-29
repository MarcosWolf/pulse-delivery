package br.marcoswolf.pulsedelivery.mapper;

import br.marcoswolf.pulsedelivery.dto.ProductDTO;
import br.marcoswolf.pulsedelivery.model.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDTO(ProductDTO dto, @MappingTarget Product entity);
}
