package com.juantartaglia.conciprisma.conciliator.Conciliation;

import com.juantartaglia.conciprisma.conciliator.utils.ConciliationType;
import com.juantartaglia.conciprisma.model.ConciliationEntity;
import com.juantartaglia.conciprisma.repository.ConciliationRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class ConciliationDBWriter implements ItemWriter<ConciliationDTO> {

    @Autowired
    ConciliationRepository conciliationRepository;

    @Override
    public void write(List<? extends ConciliationDTO> items) throws Exception {
        conciliationRepository.saveAll(items.stream().map(this::toConciliationEntity).toList());
    }

    private ConciliationEntity toConciliationEntity(ConciliationDTO c) {
        ConciliationEntity ce = new ConciliationEntity();
        ce.setConciliationType(ConciliationType.valueOf(c.getConciliationType()));
        ce.setConciliationResultDesc(c.getConciliationResult());
        ce.setTransactionId(c.getRefNumber());
        ce.setTimestamp(Timestamp.valueOf(c.createdOn));
        return ce;
    }

}
