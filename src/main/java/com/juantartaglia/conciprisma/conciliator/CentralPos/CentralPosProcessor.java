package com.juantartaglia.conciprisma.conciliator.CentralPos;

import com.juantartaglia.conciprisma.model.CentralPosEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.Optional;

@Configuration
@Log4j2
public class CentralPosProcessor implements ItemProcessor<CentralPosCSVData, CentralPosEntity> {

    @Override
    public CentralPosEntity process(CentralPosCSVData item) throws Exception {
        log.info("CentralPos Processing : { \"id\":"+ item.getId() + ", \"payerCardNumber\": \"" + item.getPayerCardNumber() + "\" }" );
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        CentralPosEntity e = new CentralPosEntity();
        Double amount = Double.valueOf(item.getAmount());
        e.setAmount(amount);
        e.setId(item.getId());
        e.setAuthCode(item.getAuthCode().substring(2,8));
        LocalDate transactionDate = LocalDate.parse(item.getTransactionDate(), dtf);

        e.setTransactionDate(transactionDate);
        String code =  Arrays.stream(item.getOperationCode().split("-")).map(String::trim).findFirst().orElse("");
        e.setOperationCode(code);
        e.setOperationCodeDesc(item.getOperationCode());
        e.setPaymentAggregator(item.getPaymentAggregator());
        LocalDate paymentDate = LocalDate.parse(item.getPaymentDate(), dtf);
        e.setPaymentDate(paymentDate);

        LocalDate presentationDate = LocalDate.parse(item.getPresentationDate(), dtf);
        e.setPresentationDate(presentationDate);

        e.setPayerCardNumber(item.getPayerCardNumber().replace("X","*"));
        return e;
    }

}
