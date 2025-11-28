package br.marcoswolf.pulsedelivery.mapper;

import br.marcoswolf.pulsedelivery.dto.OrderDTO;
import br.marcoswolf.pulsedelivery.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface OrderMapper {
    OrderDTO toDTO(Order order);
    Order toEntity(OrderDTO dto);
}