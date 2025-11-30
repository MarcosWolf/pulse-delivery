package br.marcoswolf.pulsedelivery.mapper;

import br.marcoswolf.pulsedelivery.dto.DeliveryPersonDTO;
import br.marcoswolf.pulsedelivery.model.DeliveryPerson;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DeliveryPersonMapper {
    DeliveryPersonDTO toDTO(DeliveryPerson deliveryPerson);
    DeliveryPerson toEntity(DeliveryPersonDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDeliveryPersonFromDTO(DeliveryPersonDTO dto, @MappingTarget DeliveryPerson entity);
}
