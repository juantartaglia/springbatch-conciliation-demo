package com.juantartaglia.conciprisma.conciliator.Conciliation;

import com.juantartaglia.conciprisma.conciliator.config.ConciliationResult;
import com.juantartaglia.conciprisma.model.CentralPosEntity;
import com.juantartaglia.conciprisma.model.TransactionEntity;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Conciliation {
    private List<CentralPosEntity> centralPosList;
    private TransactionEntity transaction;
    private Set<ConciliationResult> resultList;

}
