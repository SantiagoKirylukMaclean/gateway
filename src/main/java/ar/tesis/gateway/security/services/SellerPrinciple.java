package ar.tesis.gateway.security.services;

import ar.tesis.gateway.model.Seller;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SellerPrinciple implements UserDetails {

    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public SellerPrinciple(int id, String name,
                           String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static SellerPrinciple build(Seller seller) {
        List<GrantedAuthority> authorities = seller.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRoles())
        ).collect(Collectors.toList());

        return new SellerPrinciple(
                seller.getId(),
                seller.getNombre(),
                seller.getUsername(),
                seller.getEmail(),
                seller.getPassword(),
                authorities
        );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SellerPrinciple seller = (SellerPrinciple) o;
        return Objects.equals(id, seller.getId());
    }

}
