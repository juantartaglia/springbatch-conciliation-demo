package com.juantartaglia.conciprisma;

import com.juantartaglia.conciprisma.conciliator.ConciliatorJob;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableBatchProcessing
public class ConciPrismaApplication {
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private ConciliatorJob job;

	public static void main(String[] args) {
		SpringApplication.run(ConciPrismaApplication.class, args);
	}

}
