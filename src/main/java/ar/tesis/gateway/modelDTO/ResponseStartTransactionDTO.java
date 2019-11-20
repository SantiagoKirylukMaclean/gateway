package ar.tesis.gateway.modelDTO;

import ar.tesis.gateway.model.Descuento;
import lombok.Data;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ResponseStartTransactionDTO {


    @NotNull(message = "*Please provide an email")
    private String sellerUsername;

    @NotEmpty(message = "*Please provide an email")
    private String mailComprador;

    @NotNull(message = "*Please provide an email")
    private Double monto;

    private List<Descuento> descuento;
}
