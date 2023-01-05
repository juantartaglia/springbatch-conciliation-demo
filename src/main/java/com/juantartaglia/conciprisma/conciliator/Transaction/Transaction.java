package com.juantartaglia.conciprisma.conciliator.Transaction;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction implements Serializable {

    private String refNumber;
    private String payerCardNumber;
    private Timestamp transactionDate;
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
