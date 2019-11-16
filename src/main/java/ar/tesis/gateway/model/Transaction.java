package ar.tesis.gateway.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id")
    private int id;

    @Column(name = "seller_id")
    @NotEmpty(message = "*Please provide sellerId")
    private int sellerId;

    @Column(name = "mailComprador")
    @NotEmpty(message = "*Please provide your mail")
    private String mailComprador;

    @Column(name = "monto")
    @NotEmpty(message = "*Please provide your monto")
    private Double monto;

    @Column(name = "tarjeta_credito")
    private String tarjetaCredito;

    @Column(name = "cuotas")
    private String cuotas;

    @Column(name = "estado")
    private int estado;
}
