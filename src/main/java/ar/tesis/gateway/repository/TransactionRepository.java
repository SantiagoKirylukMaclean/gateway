package ar.tesis.gateway.repository;

import ar.tesis.gateway.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
