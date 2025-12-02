package br.marcoswolf.pulsedelivery.mapper;

import br.marcoswolf.pulsedelivery.dto.order.OrderDTO;
import br.marcoswolf.pulsedelivery.dto.order.OrderUpdateDTO;
import br.marcoswolf.pulsedelivery.model.*;
import br.marcoswolf.pulsedelivery.repository.CustomerRepository;
import br.marcoswolf.pulsedelivery.repository.ProductRepository;
import br.marcoswolf.pulsedelivery.repository.SellerRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public abstract class OrderMapper {

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected CustomerRepository customerRepository;

    @Autowired
    protected SellerRepository sellerRepository;

    @Mapping(target = "pickupAddress", source = "pickupAddress")
    @Mapping(target = "deliveryAddress", source = "deliveryAddress")
    public abstract OrderDTO toDTO(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    public abstract Order toEntity(OrderDTO dto);

    @AfterMapping
    protected void mapRelations(OrderDTO dto, @MappingTarget Order order) {

        if (dto.customer() != null && dto.customer().id() != null) {
            order.setCustomer(customerRepository.findById(dto.customer().id())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found")));
        }

        if (dto.seller() != null && dto.seller().id() != null) {
            order.setSeller(sellerRepository.findById(dto.seller().id())
                    .orElseThrow(() -> new IllegalArgumentException("Seller not found")));
        }

        if (dto.orderItems() != null) {
            List<OrderItem> items = dto.orderItems().stream().map(itemDTO -> {
                Product product = productRepository.findById(itemDTO.productId())
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
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "pickupAddress", ignore = true)
    @Mapping(target = "deliveryAddress", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract void updateOrderFromDTO(OrderUpdateDTO dto, @MappingTarget Order entity);
}
