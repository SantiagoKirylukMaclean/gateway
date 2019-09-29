package ar.tesis.gateway.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Entity
@Table(name = "seller")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seller_id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide your name")
    private String nombre;

    @Column(name = "apellido")
    @NotEmpty(message = "*Please provide your surname")
    private String apellido;

    @Column(name = "razonSocial")
    @NotEmpty(message = "*Please provide your razonSocial")
    private String razonSocial;

    @Column(name = "cuit")
    @NotEmpty(message = "*Please provide your cuit")
    private String cuit;

    @Column(name = "direccion")
    @NotEmpty(message = "*Please provide your direccion")
    private String direccion;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "username")
    @NotEmpty(message = "*Please provide an username")
    private String username;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "active")
    private int active;

    @Column(name = "status")
    private byte status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "market_id", referencedColumnName = "market_id")
    private Market market;

    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "configuracion_id", referencedColumnName = "configuracion_id")
    private Configuracion configuracion;
}