package com.juantartaglia.conciprisma.conciliator.Conciliation;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConciliationDTO {
    String refNumber;
    LocalDateTime transactionDate;
    String payerCardNumber;
    String channel;
    String payerCardBrand;
    Double amount;
    String authCode;
    String conciliationType;
    String conciliationResult;
    String externalRefId;
    LocalDateTime createdOn;

}
