package ar.tesis.gateway.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "autorizaciones")
public class Autorizacion {

	@Id
	@Column(name = "nro_tarjeta")
	@NotEmpty(message = "*Please provide card number")
	private String nroTarjeta;
	@Column(name = "monto_acumulado")
	@NotNull(message = "*Please provide your montoAcumulado")
	private Double montoAcumulado;
	@Column(name = "monto_maximo")
    @NotNull(message = "*Please provide your montoMaximo")
	private Double montoMaximo;

}
