package ar.tesis.gateway.service;

import ar.tesis.gateway.modelDTO.RequestTransactionDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentServiceInterface {

    public boolean validTransaction (RequestTransactionDTO requestTransactionD);
}
