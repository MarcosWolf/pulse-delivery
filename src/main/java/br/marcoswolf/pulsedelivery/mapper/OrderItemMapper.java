package br.marcoswolf.pulsedelivery.mapper;

import br.marcoswolf.pulsedelivery.dto.OrderItemDTO;
import br.marcoswolf.pulsedelivery.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel =  "spring")
public interface OrderItemMapper {
    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemDTO dto);

    OrderItemDTO toDTO(OrderItem orderItem);
}
