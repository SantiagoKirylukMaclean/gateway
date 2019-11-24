package ar.tesis.gateway.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

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
    private int cuotas;

    @Column(name = "estado")
    private int estado;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar creationDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar modificationDateTime;
}
