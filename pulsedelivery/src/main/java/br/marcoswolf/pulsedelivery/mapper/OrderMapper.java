package br.marcoswolf.pulsedelivery.mapper;

import br.marcoswolf.pulsedelivery.dto.OrderDTO;
import br.marcoswolf.pulsedelivery.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    public Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setCustomerName(dto.customerName());
        order.setStatus(dto.status());
        order.setAddress(dto.address());
        order.setCreatedAt(dto.createdAt());

        return order;
    }

    public OrderDTO toDTO(Order order) {
        return new OrderDTO(
            order.getId(),
            order.getCustomerName(),
            order.getStatus(),
            order.getAddress(),
            order.getCreatedAt()
        );
    }
}
