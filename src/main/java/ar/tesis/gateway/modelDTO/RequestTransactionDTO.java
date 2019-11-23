package ar.tesis.gateway.modelDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;


public class RequestTransactionDTO {

    @Getter
    @NotNull(message = "*Please provide an email")
    private String sellerUsername;
    @Getter
    @NotEmpty(message = "*Please provide an email")
    private String mailComprador;
    @Getter
    @NotNull(message = "*Please provide an email")
    private Double monto;
}
