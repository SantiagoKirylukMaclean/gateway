package ar.tesis.gateway.modelDTO;

import ar.tesis.gateway.model.Descuento;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ResponseApplyTransactionDTO {


    private String mesaje;


}
