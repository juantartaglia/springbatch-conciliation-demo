package com.juantartaglia.conciprisma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CentralPosEntity {

    @Id
    private String id;
    private LocalDate presentationDate;
    private String operationCode;
    private String operationCodeDesc;
    private String payerCardNumber;
    private LocalDate transactionDate;
    private LocalDate paymentDate;
    private Double amount;
    private String authCode;
    private String paymentAggregator;

}
