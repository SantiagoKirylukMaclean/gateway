package ar.tesis.gateway.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.tesis.gateway.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //TODO: Check the RoleName
    Optional<Role> findByRoles(String roleName);
}