package ar.tesis.gateway.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "fraude")
public class Fraude {

	@Id
	@Column(name = "nro_tarjeta")
	@NotEmpty(message = "*Please provide card number")
	private String nroTarjeta;

}
