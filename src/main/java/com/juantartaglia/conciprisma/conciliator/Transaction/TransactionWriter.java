package com.juantartaglia.conciprisma.conciliator.Transaction;

import com.juantartaglia.conciprisma.model.TransactionEntity;
import com.juantartaglia.conciprisma.repository.TransactionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionWriter implements ItemWriter<TransactionEntity> {

    @Autowired
    private TransactionRepository trxRepository;

    @Override
    public void write(List<? extends TransactionEntity> items) throws Exception {
        trxRepository.saveAll(items);
    }
}
