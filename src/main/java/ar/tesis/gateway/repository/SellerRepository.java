package ar.tesis.gateway.repository;


import ar.tesis.gateway.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("SellerRepository")
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByUsername(String sellerId);

}
