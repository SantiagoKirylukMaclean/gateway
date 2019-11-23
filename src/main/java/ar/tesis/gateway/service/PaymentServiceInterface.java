package ar.tesis.gateway.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ar.tesis.gateway.modelDTO.RequestApplyTransactionDTO;
import ar.tesis.gateway.modelDTO.RequestTransactionDTO;

@Service
public interface PaymentServiceInterface {

	ResponseEntity<?> startTransaction (RequestTransactionDTO requestTransactionDTO);

	ResponseEntity<?> applyTransaction (RequestApplyTransactionDTO requestApplyTransactionDTO);
}
