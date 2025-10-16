package xyz.wochib70.infrastructure.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    private Long id;

    private Long userId;

    private Long currencyId;

    private Integer balance;
}
