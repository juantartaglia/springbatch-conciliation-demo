package com.juantartaglia.conciprisma.conciliator;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/job")
public class ConciliationJobController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("transactionsJob")
    private Job transactionsJob;

    @Autowired
    @Qualifier("centralPosJob")
    private Job centralPosJob;

    @Autowired
    @Qualifier("conciliationJob")
    private Job conciliationJob;


    @GetMapping("/importTrx")
    public ResponseEntity transactionJobController(@RequestParam String fromDateStr, @RequestParam String toDateStr){
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fromDate = LocalDate.parse(fromDateStr,dtf);
            LocalDate toDate = LocalDate.parse(toDateStr,dtf);
            JobParameters jobParameters = new JobParametersBuilder().addDate("date", new Date())
                    .addLong("time", System.currentTimeMillis())
                    .addDate("fromDate", Date.from(fromDate.atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()))
                    .addDate("toDate", Date.from(toDate.atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()))
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(transactionsJob, jobParameters);
            System.out.println("STATUS :: " + execution.getStatus());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Exception e) {
            System.out.println("ERROR :: " + e.getLocalizedMessage());
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/processCsv")
    public ResponseEntity getJobController(){
        try {
            JobParameters jobParameters = new JobParametersBuilder().addDate("date", new Date())
                    .addLong("time", System.currentTimeMillis())
                    .addString("centralpos.filename", "D:\\dev\\NX\\PetProyect\\conci-prisma\\src\\main\\resources\\csv\\prisma-merged.csv")
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(centralPosJob, jobParameters);
            System.out.println("STATUS :: " + execution.getStatus());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Exception e) {
            System.out.println("ERROR :: " + e.getLocalizedMessage());
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/report")
    public ResponseEntity getReportController(){
        try {
            JobParameters jobParameters = new JobParametersBuilder().addDate("date", new Date())
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(conciliationJob, jobParameters);
            System.out.println("STATUS :: " + execution.getStatus());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Exception e) {
            System.out.println("ERROR :: " + e.getLocalizedMessage());
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
