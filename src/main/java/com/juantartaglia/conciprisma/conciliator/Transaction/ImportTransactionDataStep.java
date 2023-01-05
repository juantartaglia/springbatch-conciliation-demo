package com.juantartaglia.conciprisma.conciliator.Transaction;

import com.juantartaglia.conciprisma.model.TransactionEntity;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportTransactionDataStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Bean("transactionLoad")
    public Step transactionLoad(@Autowired @Qualifier("transactionItemReader") JdbcPagingItemReader<Transaction> reader,
                                @Autowired TransactionWriter transactionWriter,
                                @Autowired TransactionProcessor transactionProcessor) {
        return stepBuilderFactory.get("transactionLoad")
                .<Transaction, TransactionEntity>chunk(1000)
                .reader(reader)
                .processor(transactionProcessor)
                .writer(transactionWriter)
                .build();
    }
}
