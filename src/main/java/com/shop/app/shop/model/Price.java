package com.shop.app.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Price {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_generator")
    @SequenceGenerator(name = "price_generator", sequenceName = "price_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false)
    @Type(type = "uuid-char")
    @Builder.Default
    private UUID uuid = UUID.randomUUID();


    @Column(name = "PRICE_VALUE", updatable = false, nullable = false, columnDefinition = "DECIMAL(19,2)")
    private BigDecimal price;

    @Column(name = "CREATED_ON", updatable = false, nullable = false)
    private Timestamp createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PRODUCT", nullable = false)
    private Product product;

    public Price() {
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
        Price other = (Price) obj;
        return uuid.equals(other.getUuid());
    }

}
