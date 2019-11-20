package ar.tesis.gateway.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.tesis.gateway.model.Autorizacion;

@Repository("AutorizacioneRepository")
public interface AutorizacionRepository extends JpaRepository<Autorizacion, String> {


}
