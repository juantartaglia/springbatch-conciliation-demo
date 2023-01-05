package com.juantartaglia.conciprisma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

    @Id
    private String id;
    private String payerCardNumber;
    private LocalDateTime transactionDate;
    private Double amount;
    private String payerCardBrand;
    private String channel;
    private String authCode;
    private String paymentAggregator;
    private String externalRef;
    private String clientId;
    private String status;
    private String paymentMethod;

}
