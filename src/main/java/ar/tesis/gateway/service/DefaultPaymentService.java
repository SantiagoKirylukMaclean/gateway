package ar.tesis.gateway.service;

import ar.tesis.gateway.modelDTO.RequestTransactionDTO;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service("DefaultPaymentService")
public class DefaultPaymentService implements PaymentServiceInterface {

    public boolean validTransaction (RequestTransactionDTO requestTransactionDTO){

        return true;
    }
}
