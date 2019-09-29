package ar.tesis.gateway.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Entity
@Table(name = "servicio")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "servicio_id")
    private long id;

    @Column(name = "descripcion")
    @NotEmpty(message = "*Please provide your descripcion")
    private String descripcion;

    @Column(name = "costo")
    @NotEmpty(message = "*Please provide your costo")
    private Double costo;

    @OneToMany(mappedBy = "servicio")
    private Set<Seller> seller;

}
