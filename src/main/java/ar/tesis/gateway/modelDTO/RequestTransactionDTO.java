package ar.tesis.gateway.modelDTO;

import lombok.Getter;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class RequestTransactionDTO {

    @Getter
    @NotNull(message = "*Please provide an email")
    private int sellerId;
    @Getter
    @NotEmpty(message = "*Please provide an email")
    private String mailComprador;
    @Getter
    @NotNull(message = "*Please provide an email")
    private Double monto;
}
