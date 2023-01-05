package com.juantartaglia.conciprisma.conciliator;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConciliatorJob {


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean("transactionsJob")
    public Job transactionsJob(@Autowired @Qualifier("transactionLoad") Step transactionLoad) {

        return jobBuilderFactory.get("transactionsJob")
                .incrementer(new RunIdIncrementer())
                .flow(transactionLoad)
                .end()
                .build();
    }

    @Bean
    public Job centralPosJob(@Autowired @Qualifier("importCentralPosCSV") Step importCentralPosCSV) {

        return jobBuilderFactory.get("centralPosJob")
                .incrementer(new RunIdIncrementer())
                .flow(importCentralPosCSV)
                .end()
                .build();
    }

    @Bean("conciliationJob")
    public Job conciliationJob(@Autowired @Qualifier("conciliationReportStep") Step conciliationReportStep) {

        return jobBuilderFactory.get("conciliationJob")
                .incrementer(new RunIdIncrementer())
                .flow(conciliationReportStep)
                .end()
                .build();
    }

}
