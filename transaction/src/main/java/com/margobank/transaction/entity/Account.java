package com.margobank.transaction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Account extends BaseEntity {

    @Id
    private Long accountNo;
    private String customerId;
    private String accountType;
    private BigDecimal balance;
}
