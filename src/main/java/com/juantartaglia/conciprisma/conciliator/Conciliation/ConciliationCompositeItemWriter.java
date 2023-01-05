package com.juantartaglia.conciprisma.conciliator.Conciliation;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ConciliationCompositeItemWriter {

    @Bean("compositeConciliationItemWriter")
    @StepScope
    public CompositeItemWriter<ConciliationDTO> compositeConciliationItemWriter(
                                        @Autowired @Qualifier("conciliationItemWriter") FlatFileItemWriter<ConciliationDTO> conciliationWriter,
                                        @Autowired ConciliationDBWriter conciliationDBWriter) {
            CompositeItemWriter<ConciliationDTO> writer = new CompositeItemWriter<>();
            writer.setDelegates(Arrays.asList(conciliationWriter, conciliationDBWriter));
            return writer;
    }
}
