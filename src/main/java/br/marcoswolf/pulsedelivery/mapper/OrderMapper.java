package br.marcoswolf.pulsedelivery.mapper;

import br.marcoswolf.pulsedelivery.dto.OrderDTO;
import br.marcoswolf.pulsedelivery.dto.OrderUpdateDTO;
import br.marcoswolf.pulsedelivery.model.Order;
import br.marcoswolf.pulsedelivery.model.OrderItem;
import br.marcoswolf.pulsedelivery.repository.CustomerRepository;
import br.marcoswolf.pulsedelivery.repository.ProductRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public abstract class OrderMapper {

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected CustomerRepository customerRepository;

    public abstract OrderDTO toDTO(Order order);

    public abstract Order toEntity(OrderDTO dto);

    @AfterMapping
    protected void mapOrderItemsAndCustomer(OrderDTO dto, @MappingTarget Order order) {
        if (dto.customer() != null && dto.customer().id() != null) {
            order.setCustomer(customerRepository.findById(dto.customer().id())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found")));
        }

        if (dto.orderItems() != null) {
            List<OrderItem> items = dto.orderItems().stream().map(itemDTO -> {
                var product = productRepository.findById(itemDTO.productId())
                        .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                OrderItem item = new OrderItem();
                item.setProduct(product);
                item.setQuantity(itemDTO.quantity());
                item.setOrder(order);
                return item;
            }).toList();
            order.setOrderItems(items);
        }
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract void updateOrderFromDTO(OrderUpdateDTO dto, @MappingTarget Order entity);
}