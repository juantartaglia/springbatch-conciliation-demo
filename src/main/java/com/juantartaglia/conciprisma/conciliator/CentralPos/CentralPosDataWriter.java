package com.juantartaglia.conciprisma.conciliator.CentralPos;

import com.juantartaglia.conciprisma.model.CentralPosEntity;
import com.juantartaglia.conciprisma.repository.CentralPosRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CentralPosDataWriter implements ItemWriter<CentralPosEntity> {

    @Autowired
    private CentralPosRepository centralPosRepository;


    @Override
    public void write(List<? extends CentralPosEntity> items) throws Exception {
        centralPosRepository.saveAll(items);
    }
}
