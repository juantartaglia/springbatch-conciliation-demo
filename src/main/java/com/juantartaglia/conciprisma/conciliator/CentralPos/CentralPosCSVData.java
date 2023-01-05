package com.juantartaglia.conciprisma.conciliator.CentralPos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class CentralPosCSVData {
    private String id;
    private String presentationDate;
    private String operationCode;
    private String payerCardNumber;
    private String transactionDate;
    private String paymentDate;
    private String amount;
    private String authCode;
    private String paymentAggregator;
}
