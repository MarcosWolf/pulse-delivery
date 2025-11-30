package br.marcoswolf.pulsedelivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "customer_street")),
            @AttributeOverride(name = "number", column = @Column(name = "customer_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "customer_complement")),
            @AttributeOverride(name = "neighborhood", column = @Column(name = "customer_neighborhood")),
            @AttributeOverride(name = "city", column = @Column(name = "customer_city")),
            @AttributeOverride(name = "state", column = @Column(name = "customer_state")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "customer_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "customer_country"))
    })
    private Address address;
}