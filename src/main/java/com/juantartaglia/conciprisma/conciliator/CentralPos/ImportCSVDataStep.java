package com.juantartaglia.conciprisma.conciliator.CentralPos;

import com.juantartaglia.conciprisma.model.CentralPosEntity;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportCSVDataStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Bean("importCentralPosCSV")
    public Step importCentralPosCSV(@Autowired @Qualifier("centralPosCSVItemReader") FlatFileItemReader<CentralPosCSVData> centralPosCSVItemReader,
                                @Autowired CentralPosDataWriter centralPosDataWriter,
                                @Autowired CentralPosProcessor centralPosProcessor) {
        return stepBuilderFactory.get("importCentralPosCSV")
                .<CentralPosCSVData, CentralPosEntity>chunk(500)
                .reader(centralPosCSVItemReader)
                .processor(centralPosProcessor)
                .writer(centralPosDataWriter)
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipLimit(100)
                .build();
    }
}
