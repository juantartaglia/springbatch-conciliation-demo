package com.juantartaglia.conciprisma.conciliator.Conciliation;

import com.juantartaglia.conciprisma.conciliator.config.ConciliationResult;
import com.juantartaglia.conciprisma.conciliator.utils.ConciliationType;
import com.juantartaglia.conciprisma.model.CentralPosEntity;
import com.juantartaglia.conciprisma.model.TransactionEntity;
import com.juantartaglia.conciprisma.repository.CentralPosRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@Log4j2
public class ConciliationProcessor implements ItemProcessor<TransactionEntity, ConciliationDTO> {

    @Autowired
    private CentralPosRepository centralPosRepository;

    private static final Integer TRX_PAN_LENGTH = 16;

    @Override
    public ConciliationDTO process(TransactionEntity item) throws Exception {
        Conciliation conciliation = new Conciliation();
        Set<ConciliationResult> resultConciliationSet = new HashSet<ConciliationResult>();

        conciliation.setTransaction(item);

        log.info("processing -> refNumber : " + item.getId() + ", transactionDate: " + item.getTransactionDate().toLocalDate().toString());

        List<CentralPosEntity> centralPosList;
        if (item.getPayerCardNumber().charAt(13) != '*') {
            centralPosList = centralPosRepository.findByTransactionDateAndPayerCardNumberAndAmountAndAuthCode(
                    item.getTransactionDate().toLocalDate(),
                    item.getPayerCardNumber(),
                    item.getAmount(),
                    item.getAuthCode()
            );
            if (centralPosList.isEmpty()) {
                centralPosList = centralPosRepository.findByFuzzyTransactionDateAndPayerCardNumberAndAmountAndAuthCode(
                        item.getTransactionDate().toLocalDate(),
                        item.getPayerCardNumber(),
                        item.getAmount(),
                        item.getAuthCode(),
                        2
                );
            }
        }
        else {
            String bin = item.getPayerCardNumber().substring(0,6);
            centralPosList = centralPosRepository.findByTransactionDateAndBINAndAmountAndAuthCode(
                    item.getTransactionDate().toLocalDate(),
                    bin,
                    item.getAmount(),
                    item.getAuthCode()
            );
            if (centralPosList.isEmpty()) {
                centralPosList = centralPosRepository.findByFuzzyTransactionDateAndBINAndAmountAndAuthCode(
                        item.getTransactionDate().toLocalDate(),
                        bin,
                        item.getAmount(),
                        item.getAuthCode(),
                        2
                );
            }
        }


        if (centralPosList.isEmpty()) {
            resultConciliationSet.add(ConciliationResult.NOT_CONCILIATED);
        }
        else {
            conciliation.setCentralPosList(centralPosList);
            resultConciliationSet.add(ConciliationResult.CONCILIATED_OK);

            if (centralPosList.stream().map(c -> c.getOperationCode()).anyMatch(s -> s.equals("0005"))) {
                resultConciliationSet.add(ConciliationResult.CHARGE);
            }
            if (centralPosList.stream().map(c -> c.getOperationCode()).anyMatch(s -> s.equals("1517") || s.equals("1507") )) {
                resultConciliationSet.add(ConciliationResult.CHARGEBACK);
            }
            if (!centralPosList.stream().filter(s -> s.getOperationCode().equals("0005")).findFirst().stream().anyMatch(c -> c.getTransactionDate().atStartOfDay().equals(conciliation.getTransaction().getTransactionDate().toLocalDate().atStartOfDay()))  ) {
                resultConciliationSet.add(ConciliationResult.ABNORMAL_DATE);
            }
            if (!centralPosList.stream().filter(s -> s.getOperationCode().equals("0005")).findFirst().stream().anyMatch(c -> c.getAuthCode().equals(conciliation.getTransaction().getAuthCode()))) {
                resultConciliationSet.add(ConciliationResult.ABNORMAL_AUTHCODE);
            }
            if (!centralPosList.stream().filter(s -> s.getOperationCode().equals("0005")).findFirst().stream().anyMatch(c -> c.getPayerCardNumber().equals(conciliation.getTransaction().getPayerCardNumber()))  ) {
                resultConciliationSet.add(ConciliationResult.ABNORMAL_PAN);
            }
        }

        conciliation.setResultList(resultConciliationSet);

        return toConciliationDTO(conciliation);
    }

    private ConciliationDTO toConciliationDTO(Conciliation c) {
        ConciliationDTO dto = new ConciliationDTO();
            dto.setAmount(c.getTransaction().getAmount());
            dto.setChannel(c.getTransaction().getChannel());
            dto.setAuthCode(c.getTransaction().getAuthCode());
            dto.setCreatedOn(LocalDateTime.now());
            dto.setTransactionDate(c.getTransaction().getTransactionDate());
            dto.setPayerCardNumber(c.getTransaction().getPayerCardNumber());
            dto.setPayerCardBrand(c.getTransaction().getPayerCardBrand());
            dto.setRefNumber(c.getTransaction().getId());
            dto.setConciliationResult(c.getResultList().stream().map(i -> i.getDescription()).collect(Collectors.joining(" - ")));
            dto.setConciliationType(ConciliationType.PAYMENT_PROCESSOR.name());
            if (c.getCentralPosList()!=null && !c.getCentralPosList().isEmpty()) {
                dto.setExternalRefId(c.getCentralPosList().stream().map(i -> i.getId()).collect(Collectors.joining(" - ")));
            } else {
                dto.setExternalRefId("NA");
            }
        return dto;
    }

}
