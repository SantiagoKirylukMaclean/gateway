package ar.tesis.gateway.modelDTO;

import java.util.Set;

import ar.tesis.gateway.model.Descuento;
import lombok.Data;

@Data
public class ResponseStartTransactionDTO {

    private String sellerUsername;
    private String mailComprador;
    private Double monto;
    private long transaccionId;
    private Set<Descuento> descuento;
}
