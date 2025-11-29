package br.marcoswolf.pulsedelivery.mapper;

import br.marcoswolf.pulsedelivery.dto.OrderItemDTO;
import br.marcoswolf.pulsedelivery.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel =  "spring")
public interface OrderItemMapper {
    OrderItemDTO toDTO(OrderItem orderItem);
    OrderItem toEntity(OrderItemDTO dto);
}
