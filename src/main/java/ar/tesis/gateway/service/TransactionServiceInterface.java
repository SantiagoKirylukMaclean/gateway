package ar.tesis.gateway.service;

import ar.tesis.gateway.model.Seller;
import ar.tesis.gateway.model.Transaction;
import ar.tesis.gateway.modelDTO.RequestApplyTransactionDTO;
import ar.tesis.gateway.modelDTO.RequestTransactionDTO;
import ar.tesis.gateway.modelDTO.ResponseApplyTransactionDTO;
import ar.tesis.gateway.modelDTO.ResponseStartTransactionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionServiceInterface {

    List<Transaction> findIncompleteTrnsactions(String seller);
}
