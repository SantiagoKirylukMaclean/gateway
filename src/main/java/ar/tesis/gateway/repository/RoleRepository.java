package ar.tesis.gateway.repository;

import java.util.Optional;

import ar.tesis.gateway.model.Role;
import ar.tesis.gateway.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //TODO: Check the RoleName
    Optional<Role> findByRoles(String roleName);
}