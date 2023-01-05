package com.juantartaglia.conciprisma.conciliator.Transaction;

import com.juantartaglia.conciprisma.model.TransactionEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class TransactionProcessor implements  ItemProcessor<Transaction, TransactionEntity>{


    @Override
    public TransactionEntity process(Transaction item) throws Exception {
        log.info("PROCESSING: { refNumber : " + item.getRefNumber() + "}");

        TransactionEntity  trx = new TransactionEntity();
        trx.setId(item.getRefNumber());
        trx.setAmount(item.getAmount());
        trx.setChannel(item.getChannel());
        trx.setAuthCode(item.getAuthCode());
        trx.setStatus(item.getStatus());
        trx.setClientId(item.getClientId());
        trx.setExternalRef(item.getExternalRef());
        trx.setTransactionDate(item.getTransactionDate().toLocalDateTime());
        trx.setPayerCardBrand(item.getPayerCardBrand());
        trx.setPayerCardNumber(item.getPayerCardNumber());
        trx.setPaymentMethod(item.getPaymentMethod());
        trx.setClientId(item.getClientId());
        trx.setPaymentAggregator(item.getPaymentAggregator());
        return trx;
    }
}
