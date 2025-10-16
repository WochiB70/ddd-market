package xyz.wochib70.infrastructure.currency;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.currency.CurrencyStatus;

@Getter
@Setter
@Entity
@Table(name = "currency")
public class CurrencyEntity {

    @Id
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private CurrencyStatus status;

    private Integer referenceCount;
}
