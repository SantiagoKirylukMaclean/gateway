package ar.tesis.gateway.service;

import ar.tesis.gateway.model.Seller;
import ar.tesis.gateway.model.Transaction;
import ar.tesis.gateway.modelDTO.RequestApplyTransactionDTO;
import ar.tesis.gateway.modelDTO.RequestTransactionDTO;
import ar.tesis.gateway.modelDTO.ResponseStartTransactionDTO;
import ar.tesis.gateway.repository.SellerRepository;
import ar.tesis.gateway.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Service("DefaultPaymentService")
public class DefaultPaymentService implements PaymentServiceInterface {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public ResponseStartTransactionDTO startTransaction (RequestTransactionDTO requestTransactionDTO){

        ResponseStartTransactionDTO respose = new ResponseStartTransactionDTO();

        if (validTransaction(requestTransactionDTO) == true){
            respose.setSellerUsername(requestTransactionDTO.getSellerUsername());
            respose.setMailComprador(requestTransactionDTO.getMailComprador());
            respose.setMonto(requestTransactionDTO.getMonto());
            respose.setDescuento(sellerRepository.findByUsername(requestTransactionDTO.getSellerUsername())
                    .get().getDescuento());
        }

        return respose;
    }

    public void applyTransaction (RequestApplyTransactionDTO requestApplyTransactionDTO){
        Seller seller = sellerRepository.findByUsername(requestApplyTransactionDTO.getSellerUsername()).get();
        Transaction transaction = new Transaction();
        transaction.setMailComprador(requestApplyTransactionDTO.getMailComprador());
        transaction.setMonto(requestApplyTransactionDTO.getMonto());
        transaction.setTarjetaCredito(requestApplyTransactionDTO.getTarjetaComprador());
        transaction.setCuotas("1");
        transaction.setEstado(1);
        Set<Transaction> transacciones = seller.getTransaction() == null ? new HashSet<>() : seller.getTransaction();
        transacciones.add(transaction);
        seller.setTransaction(transacciones);
        sellerRepository.save(seller);

    }

    private boolean validTransaction (RequestTransactionDTO requestTransactionDTO){

        Optional<Seller> optional = sellerRepository.findByUsername(requestTransactionDTO.getSellerUsername());

        boolean existeUsuario = optional.isPresent();

        return existeUsuario;
    }
}
