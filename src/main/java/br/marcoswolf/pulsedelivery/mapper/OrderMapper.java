package br.marcoswolf.pulsedelivery.mapper;

import br.marcoswolf.pulsedelivery.dto.OrderDTO;
import br.marcoswolf.pulsedelivery.model.Order;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, OrderItemMapper.class})
public interface OrderMapper {
    OrderDTO toDTO(Order order);
    Order toEntity(OrderDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateOrderFromDTO(OrderDTO dto, @MappingTarget Order entity);
}