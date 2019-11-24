package ar.tesis.gateway.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ar.tesis.gateway.modelDTO.RequestApplyTransactionDTO;
import ar.tesis.gateway.modelDTO.RequestTransactionDTO;
import ar.tesis.gateway.modelDTO.ResponseApplyTransactionDTO;
import ar.tesis.gateway.modelDTO.ResponseStartTransactionDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentServiceInterface {

	ResponseEntity<?> startTransaction (RequestTransactionDTO requestTransactionDTO);

	ResponseEntity<?> applyTransaction (RequestApplyTransactionDTO requestApplyTransactionDTO);
}
