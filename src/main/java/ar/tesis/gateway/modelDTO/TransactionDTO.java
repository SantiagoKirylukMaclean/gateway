package ar.tesis.gateway.modelDTO;

import lombok.Getter;

public class TransactionDTO {

    @Getter
    private int sellerId;
    @Getter
    private String mailComprador;
    @Getter
    private Double monto;
    @Getter
    private String tarjetaCredito;
    @Getter
    private String cuotas;
    @Getter
    private int estado;
}
