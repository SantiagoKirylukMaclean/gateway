package ar.tesis.gateway.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "descuentos")
public class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "descuentos_id")
    private long id;

    //@ManyToOne
    //@JoinColumn(name = "seller_id", nullable = false)
    //private Seller seller;

    @Column(name = "descripcion")
    @NotEmpty(message = "*Please provide your description")
    private String descripcion;

    @Column(name = "tarjeta_credito")
    @NotEmpty(message = "*Please provide your description")
    private String tarjetaCredito;

    @Column(name = "descuento")
    @NotNull(message = "*Please provide your description")
    private int descuento;


}
