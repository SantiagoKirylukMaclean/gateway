package ar.tesis.gateway.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "market")
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "market_id")
    private int id;

    @Column(name = "nombre")
    @NotEmpty(message = "*Please provide your nombre")
    private String nombre;

    @Column(name = "telefono")
    @NotEmpty(message = "*Please provide your telefono")
    private String telefono;

    @Column(name = "URL")
    @NotEmpty(message = "*Please provide your URL")
    private String URL;

    @Column(name = "codigoPais")
    @NotEmpty(message = "*Please provide your codigoPais")
    private String codigoPais;

    @Column(name = "categoriaMercante")
    @NotEmpty(message = "*Please provide your categoriaMercante")
    private String categoriaMercante;

    @OneToOne(mappedBy = "market")
    @JsonIgnore
    private Seller seller;

}
