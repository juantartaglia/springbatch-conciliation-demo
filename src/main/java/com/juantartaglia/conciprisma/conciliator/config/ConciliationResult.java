package com.juantartaglia.conciprisma.conciliator.config;

public enum ConciliationResult {
    ABNORMAL_AUTHCODE("Codigo de Autorizacion anormal"),
    ABNORMAL_DATE("Fecha anormal"),
    ABNORMAL_PAN("Numero de Tarjeta anormal"),

    CHARGEBACK("Contracargo"),
    CHARGE("Cargo"),
    CONCILIATED_OK("Conciliado Correctamente"),
    CONCILIATED_WITH_OBS("Conciliado con Observaciones"),
    DIF_AMOUNT("Diferencia de importes"),
    NOT_CONCILIATED("No Conciliado"),
    TRANSACTION_NOT_FOUND("Transaccion no encontrada");

    private final String description;

    ConciliationResult(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
