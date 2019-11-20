package ar.tesis.gateway.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.tesis.gateway.model.Fraude;

@Repository("FraudeRepository")
public interface FraudeRepository extends JpaRepository<Fraude, String> {


}
