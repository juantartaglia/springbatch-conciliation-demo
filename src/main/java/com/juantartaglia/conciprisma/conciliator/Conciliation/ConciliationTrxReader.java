package com.juantartaglia.conciprisma.conciliator.Conciliation;

import com.juantartaglia.conciprisma.model.TransactionEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component("conciliationTrxReader")
public class ConciliationTrxReader {

    private static final String SQL_QUERY = """
            select e
            from TransactionEntity e
            where (DATE(e.transactionDate) between '2022-04-15' AND '2022-05-31')
            ORDER BY e.transactionDate
            """;

       @Bean("trxItemReader")
    @StepScope
    public JpaPagingItemReader<TransactionEntity> trxItemReader (
            @Autowired EntityManagerFactory entityManagerFactory
    ) {

        return new JpaPagingItemReaderBuilder<TransactionEntity>()
                .name("trxItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString(SQL_QUERY)
                .pageSize(2000)
                .build();
    }

}
