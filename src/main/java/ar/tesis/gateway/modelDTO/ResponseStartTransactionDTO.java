package ar.tesis.gateway.modelDTO;

import ar.tesis.gateway.model.Descuento;
import lombok.Data;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
public class ResponseStartTransactionDTO {


    @NotNull(message = "*Please provide an email")
    private String sellerUsername;

    @NotNull(message = "*Please provide an email")
    private Double monto;

    private long transaccionId;

    private Set<Descuento> descuento;
}
