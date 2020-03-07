package com.shop.app.shop.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SINGLE_ORDER")
public class SingleOrder {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type="uuid-char")
    private UUID id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_PRICE")
    private Price price ;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_PRODUCT", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_BASKET", nullable = false)
    private OrderBasket basket;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SingleOrder other = (SingleOrder) obj;
        return id.equals(other.getId());
    }
}
