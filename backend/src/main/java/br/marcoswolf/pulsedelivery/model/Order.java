package br.marcoswolf.pulsedelivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "pickup_street")),
            @AttributeOverride(name = "number", column = @Column(name = "pickup_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "pickup_complement")),
            @AttributeOverride(name = "neighborhood", column = @Column(name = "pickup_neighborhood")),
            @AttributeOverride(name = "city", column = @Column(name = "pickup_city")),
            @AttributeOverride(name = "state", column = @Column(name = "pickup_state")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "pickup_postalCode")),
            @AttributeOverride(name = "country", column = @Column(name = "pickup_country"))
    })
    private Address pickupAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "delivery_street")),
            @AttributeOverride(name = "number", column = @Column(name = "delivery_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "delivery_complement")),
            @AttributeOverride(name = "neighborhood", column = @Column(name = "delivery_neighborhood")),
            @AttributeOverride(name = "city", column = @Column(name = "delivery_city")),
            @AttributeOverride(name = "state", column = @Column(name = "delivery_state")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "delivery_postalCode")),
            @AttributeOverride(name = "country", column = @Column(name = "delivery_country"))
    })
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
}