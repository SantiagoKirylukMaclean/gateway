package ar.tesis.gateway.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id")
    private long id;

    //Borrar de aca
    //@ManyToOne
    //@JoinColumn(name = "seller_id", nullable = false)
    //private Seller seller;

    @Column(name = "mailComprador")
    @NotEmpty(message = "*Please provide your mail")
    private String mailComprador;

    @Column(name = "monto")
    @NotNull(message = "*Please provide your monto")
    private Double monto;

    @Column(name = "tarjeta_credito")
    private String tarjetaCredito;

    @Column(name = "cuotas")
    private String cuotas;

    @Column(name = "estado")
    private int estado;
}
