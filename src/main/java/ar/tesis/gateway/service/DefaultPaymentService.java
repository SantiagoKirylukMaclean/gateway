package ar.tesis.gateway.service;

import ar.tesis.gateway.model.Seller;
import ar.tesis.gateway.modelDTO.RequestTransactionDTO;
import ar.tesis.gateway.repository.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Service("DefaultPaymentService")
public class DefaultPaymentService implements PaymentServiceInterface {

    @Autowired
    SellerRepository sellerRepository;

    public boolean validTransaction (RequestTransactionDTO requestTransactionDTO){


        Optional<Seller> optional = sellerRepository.findByUsername(requestTransactionDTO.getSellerUsername());
        boolean existeUsuario = optional.isPresent();
        optional.ifPresent(seller -> {
            log.info("Hay algo");
        });

        return existeUsuario;
    }
}
