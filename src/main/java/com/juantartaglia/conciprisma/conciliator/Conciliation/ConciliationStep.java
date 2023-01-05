package com.juantartaglia.conciprisma.conciliator.Conciliation;

import com.juantartaglia.conciprisma.model.TransactionEntity;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConciliationStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Bean("conciliationReportStep")
    public Step conciliationReportStep(@Autowired @Qualifier("trxItemReader") JpaPagingItemReader<TransactionEntity> reader,
                                @Autowired @Qualifier("compositeConciliationItemWriter") CompositeItemWriter<ConciliationDTO> compositeConciliationItemWriter,
                                @Autowired ConciliationProcessor conciliationProcessor) {
        return stepBuilderFactory.get("conciliationReportStep")
                .<TransactionEntity, ConciliationDTO>chunk(1000)
                .reader(reader)
                .processor(conciliationProcessor)
                .writer(compositeConciliationItemWriter)
                .build();
    }


}
