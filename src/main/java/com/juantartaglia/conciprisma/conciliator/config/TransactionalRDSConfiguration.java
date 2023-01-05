package com.juantartaglia.conciprisma.conciliator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class TransactionalRDSConfiguration {


    @Value("${transactional-rds.driverClassName}")
    private String DB_DRIVER;

    @Value("${transactional-rds.jdbcUrl}")
    private String DB_URL;

    @Value("${transactional-rds.username}")
    private String DB_USER;

    @Value("${transactional-rds.password}")
    private String DB_PASSWORD;


    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    DataSource springBatchDb() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        return builder.build();
    }

    @Bean("transactionalDataSource")
    public DataSource transactinalDataSource() {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(DB_DRIVER);
        dataSourceBuilder.url(DB_URL);
        dataSourceBuilder.username(DB_USER);
        dataSourceBuilder.password(DB_PASSWORD);
        return dataSourceBuilder.build();
    }

}
