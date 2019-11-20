package ar.tesis.gateway.service;

import ar.tesis.gateway.modelDTO.RequestApplyTransactionDTO;
import ar.tesis.gateway.modelDTO.RequestTransactionDTO;
import ar.tesis.gateway.modelDTO.ResponseStartTransactionDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentServiceInterface {

    ResponseStartTransactionDTO startTransaction (RequestTransactionDTO requestTransactionDTO);

    void applyTransaction (RequestApplyTransactionDTO requestApplyTransactionDTO);
}
