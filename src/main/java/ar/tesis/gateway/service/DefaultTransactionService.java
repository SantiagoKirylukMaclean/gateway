package ar.tesis.gateway.service;

import ar.tesis.gateway.model.Transaction;
import ar.tesis.gateway.repository.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("DefaultSellerService")
public class DefaultTransactionService implements TransactionServiceInterface {

    @Autowired
    SellerRepository sellerRepository;





    public List<Transaction> findIncompleteTrnsactions(String seller){
        return sellerRepository.findByUsername(seller).get().getTransaction().stream().filter(trx -> trx.getEstado() == 1 )
                .collect(Collectors.toList());
    }

}
