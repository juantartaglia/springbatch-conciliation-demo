package com.juantartaglia.conciprisma.conciliator.Transaction;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component("transactionDataReader")
@StepScope
public class TransactionReader {

    @Bean("transactionItemReader")
    @StepScope
    public JdbcPagingItemReader<Transaction> getPaginationReader(@Qualifier("transactionalDataSource")DataSource dataSource) throws Exception {

        Map<String, Object> parameterValues = new HashMap<>();
        final TransactionMapper transactionMapper = new TransactionMapper();

        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();

        provider.setSelectClause("select refNumber,payerCardNumber,transactionDate,amount,payerCardBrand,channel,authCode,paymentAggregator,externalRef,clientId,status");
        provider.setFromClause("FROM transaction");
        provider.setWhereClause("WHERE status = 'CONFIRMED' and paymentMethod in ('DEBIT', 'CREDIT') and payerCardBrand in ('VISA', 'MASTER', 'MASTERCARD' ,'CABAL', 'MAESTRO') and DATE(transactionDate) between '2022-08-01' and '2022-10-31'");

        Map<String, Order> orderById = new HashMap<>();
        orderById.put("refNumber", Order.ASCENDING);
        provider.setSortKeys(orderById);


        return new JdbcPagingItemReaderBuilder<Transaction>()
                .name("getPaginationReader")
                .dataSource(dataSource)
                .queryProvider((PagingQueryProvider) provider)
                .parameterValues(parameterValues)
                .rowMapper(transactionMapper)
                .pageSize(2000)
                .build();
    }

}
