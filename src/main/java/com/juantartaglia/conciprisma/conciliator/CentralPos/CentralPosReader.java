package com.juantartaglia.conciprisma.conciliator.CentralPos;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component("centralPosReader")
@StepScope
public class CentralPosReader {


    @Bean
    @StepScope
    public FlatFileItemReader<CentralPosCSVData> centralPosCSVItemReader(
                @Value("#{jobParameters['centralpos.filename']}") String inputFile) {
        return new FlatFileItemReaderBuilder<CentralPosCSVData>()
                .name("centralPosCSVItemReader")
                .resource( new FileSystemResource(inputFile))
                .delimited()
                .delimiter(",")
                .names(new String[] {"id","archivo_id","empresa","fpres","tipo_reg","num_com","cod_op","tipo_aplic","lote","cod_bco","cod_casa","bco_est","casa_est",
                        "num_tar","forig_compra","fpag","num_comp","importe","signo","num_aut","num_cuot","plan_cuot","rec_acep",
                        "rech_princ","rech_secun","imp_plan","signo_plan","mca_pex","nro_liq","cco_origen","cco_motivo","moneda",
                        "promo_bonif_usu","promo_bonif_est","id_promo","dto_promo","signo_dto_promo","id_iva_cf","dealer","cuit_est",
                        "fpago_aju_lqe","cod_motivo_aju_lqe","porcdto_arancel","arancel","signo_arancel","tna","costo_fin",
                        "signo_cf","bines","num_est","costo_finan_direct","id_deb_aut","revisado","consumo_id","agencia_viaje","id_tx","tipo_plan","bandera_est",
                        "cco_motivo_mc","dias_pago","id_cargoliq","subcodigo","term_captura","id_consumo","producto","nombre_banco"})
                .fieldSetMapper(new CentralPosMapper())
                .linesToSkip(1)
                .build();
    }

}
