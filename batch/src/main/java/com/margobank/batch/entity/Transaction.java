package com.margobank.batch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Transaction extends BaseEntity {

    @Id
    private Long trxRefNo;
    private Long accountNumber;
    private BigDecimal trxAmount;
    private String description;
    private LocalDateTime trxDate;
    private LocalDateTime trxTime;
    private String customerId;

}
