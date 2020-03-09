package com.shop.app.shop.model;


import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "SINGLE_ORDER")
public class SingleOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "single_order_generator")
    @SequenceGenerator(name = "single_order_generator", sequenceName = "single_order_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false)
    @Type(type = "uuid-char")
    @Builder.Default
    private UUID uuid = UUID.randomUUID();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_PRICE")
    private Price price;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_PRODUCT", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_BASKET", nullable = false)
    private OrderBasket basket;

    public SingleOrder(){
        this.uuid = UUID.randomUUID();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
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
        return uuid.equals(other.getUuid());
    }
}
