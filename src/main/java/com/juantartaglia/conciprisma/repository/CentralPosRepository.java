package com.juantartaglia.conciprisma.repository;

import com.juantartaglia.conciprisma.model.CentralPosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CentralPosRepository extends JpaRepository<CentralPosEntity, String> {

    @Query("SELECT c FROM CentralPosEntity c WHERE (DATE(c.transactionDate) BETWEEN subdate(:transactionDate, 1) and adddate(:transactionDate,1)) AND c.payerCardNumber LIKE :payerCardNumber% AND FLOOR(c.amount) = FLOOR(:amount) and ((''=:authCode) OR (c.authCode = :authCode))")
    public List<CentralPosEntity> findByTransactionDateAndPayerCardNumberAndAmountAndAuthCode(LocalDate transactionDate, @Param("payerCardNumber") String payerCardNumber,
                                                                                              Double amount, String authCode
    );

    @Query("SELECT c FROM CentralPosEntity c WHERE (DATE(c.transactionDate) BETWEEN subdate(:transactionDate, 1) and adddate(:transactionDate,1)) AND (c.payerCardNumber LIKE :bin%) AND FLOOR(c.amount) = FLOOR(:amount) and ((''=:authCode) OR (c.authCode = :authCode))")
    public List<CentralPosEntity> findByTransactionDateAndBINAndAmountAndAuthCode(LocalDate transactionDate, @Param("bin") String bin,
                                                                                              Double amount, String authCode
    );

    @Query("SELECT c FROM CentralPosEntity c WHERE (DATE(c.transactionDate) BETWEEN subdate(:transactionDate, :dateLimit) and adddate(:transactionDate, :dateLimit)) AND c.payerCardNumber LIKE :payerCardNumber% AND FLOOR(c.amount) = FLOOR(:amount) and ((''=:authCode) OR (c.authCode = :authCode))")
    public List<CentralPosEntity> findByFuzzyTransactionDateAndPayerCardNumberAndAmountAndAuthCode(LocalDate transactionDate, @Param("payerCardNumber") String payerCardNumber,
                                                                                              Double amount, String authCode, Integer dateLimit
    );

    @Query("SELECT c FROM CentralPosEntity c WHERE (DATE(c.transactionDate) BETWEEN subdate(:transactionDate, :dateLimit) and adddate(:transactionDate, :dateLimit)) AND (c.payerCardNumber LIKE :bin%) AND FLOOR(c.amount) = FLOOR(:amount) and ((''=:authCode) OR (c.authCode = :authCode))")
    public List<CentralPosEntity> findByFuzzyTransactionDateAndBINAndAmountAndAuthCode(LocalDate transactionDate, @Param("bin") String bin,
                                                                                  Double amount, String authCode, Integer dateLimit
    );

}
