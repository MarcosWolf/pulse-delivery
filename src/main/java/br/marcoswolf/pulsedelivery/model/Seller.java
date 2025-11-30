package br.marcoswolf.pulsedelivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String document;
    private String phone;
    private String email;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "seller_street")),
            @AttributeOverride(name = "number", column = @Column(name = "seller_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "seller_complement")),
            @AttributeOverride(name = "neighborhood", column = @Column(name = "seller_neighborhood")),
            @AttributeOverride(name = "city", column = @Column(name = "seller_city")),
            @AttributeOverride(name = "state", column = @Column(name = "seller_state")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "seller_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "seller_country"))
    })
    private Address address;
}
