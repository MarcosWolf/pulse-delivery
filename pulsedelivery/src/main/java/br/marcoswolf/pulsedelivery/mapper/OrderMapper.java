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
        order.setAddress(dto.address());
        order.setStatus(dto.status());
        order.setCreatedAt(dto.createdAt());

        return order;
    }

    public OrderDTO toDTO(Order order) {
        return new OrderDTO(
            order.getId(),
            order.getCustomerName(),
            order.getAddress(),
            order.getStatus(),
            order.getCreatedAt()
        );
    }
}
