package ar.tesis.gateway.modelDTO;

import ar.tesis.gateway.model.Descuento;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class RequestApplyTransactionDTO {

    @Getter
    @NotNull(message = "*Please provide an email")
    private String sellerUsername;

    @Getter
    @NotEmpty(message = "*Please provide an email")
    private String mailComprador;

    @Getter
    @NotNull(message = "*Please provide an email")
    private Double monto;

    @Getter
    @NotNull(message = "*Please provide an email")
    private Descuento descuento;

    @Getter
    @NotNull(message = "*Please provide an email")
    private String tarjetaComprador;

    @Getter
    @NotNull(message = "*Please provide an email")
    private String fechaVencimiento;

    @Getter
    @NotNull(message = "*Please provide an email")
    private String nombrePlastico;

    @Getter
    @NotNull(message = "*Please provide an email")
    private Long transaccionId;

    @Getter
    @NotNull(message = "*Please provide an email")
    private int CCV;
}
