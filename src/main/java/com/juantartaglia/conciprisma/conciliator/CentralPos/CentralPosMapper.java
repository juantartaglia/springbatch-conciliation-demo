package com.juantartaglia.conciprisma.conciliator.CentralPos;

import com.juantartaglia.conciprisma.conciliator.CentralPos.CentralPosCSVData;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.ZoneId;

public class CentralPosMapper implements FieldSetMapper<CentralPosCSVData> {

    @Override
    public CentralPosCSVData mapFieldSet(FieldSet fieldSet) throws BindException {

        return new CentralPosCSVData(
                fieldSet.readString("id"),
                fieldSet.readString("fpres"),
                //fieldSet.readDate("fpres", "dd/MM/yyyy").toInstant()
                //        .atZone(ZoneId.systemDefault())
                //        .toLocalDate(),
                fieldSet.readString("cod_op"),
                fieldSet.readString("num_tar"),
                /*
                fieldSet.readDate("forig_compra", "dd/MM/yyyy").toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate(),

                 */
                fieldSet.readString("forig_compra"),
                fieldSet.readString("fpag"),
                /*
                fieldSet.readDate("fpag", "dd/MM/yyyy").toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate(),

                 */
                fieldSet.readString("importe"),
                fieldSet.readString("num_aut"),
                fieldSet.readString("num_est")
        );

    }
}
