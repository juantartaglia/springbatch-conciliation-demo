package com.juantartaglia.conciprisma.conciliator.Conciliation;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.Writer;

@Configuration
public class ConciliationWriter {

    private Resource outputResource = new FileSystemResource("output/outputData.csv");

    private final static String[] names = new String[] {
        "refNumber",
        "transactionDate",
        "payerCardNumber",
        "channel",
        "payerCardBrand",
        "amount",
        "authCode",
        "conciliationType",
        "conciliationResult",
        "externalRefId",
        "createdOn"
    };

    @Bean("conciliationItemWriter")
    @StepScope
    public FlatFileItemWriter<ConciliationDTO> conciliationItemWriter() {

        return new FlatFileItemWriterBuilder<ConciliationDTO>()
                .name("conciliationItemWriter")
                .resource(outputResource)
                .delimited()
                .delimiter(",")
                .names(names)
                .shouldDeleteIfExists(true)
                .headerCallback(new FlatFileHeaderCallback() {
                    @Override
                    public void writeHeader(Writer writer) throws IOException {
                        writer.write(String.join(",", names));
                    }
                })
                .build();
    }
}
