package br.marcoswolf.pulsedelivery.mapper;

import br.marcoswolf.pulsedelivery.dto.OrderItemDTO;
import br.marcoswolf.pulsedelivery.model.OrderItem;
import br.marcoswolf.pulsedelivery.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel =  "spring")
public interface OrderItemMapper {

    @Mapping(target = "order", ignore = true)
    @Mapping(source = "dto.productId", target = "product")
    OrderItem toEntity(OrderItemDTO dto);

    @Mapping(source = "product.id", target = "productId")
    OrderItemDTO toDTO(OrderItem orderItem);

    default Product map(Long productId) {
        if (productId == null) return null;
        Product p = new Product();
        p.setId(productId);
        return p;
    }
}