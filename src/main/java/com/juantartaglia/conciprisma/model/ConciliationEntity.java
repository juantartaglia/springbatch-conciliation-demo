package com.juantartaglia.conciprisma.model;

import com.juantartaglia.conciprisma.conciliator.utils.ConciliationType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class ConciliationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp timestamp;

    private String transactionId;

    private String conciliationResultDesc;

    private ConciliationType conciliationType;

}
