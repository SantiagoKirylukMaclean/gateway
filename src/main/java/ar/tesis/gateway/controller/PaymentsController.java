package ar.tesis.gateway.controller;

import ar.tesis.gateway.model.Payment;
import ar.tesis.gateway.modelDTO.*;
import ar.tesis.gateway.repository.UserRepository;
import ar.tesis.gateway.service.PaymentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payments")
public class PaymentsController {



    @Autowired
    PaymentServiceInterface paymentServiceInterface;



    @RequestMapping(value = "/start", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> startPayment(@Valid @RequestBody RequestTransactionDTO RequestTransactionDTO, @RequestHeader HttpHeaders headers) {
    	
        return paymentServiceInterface.startTransaction(RequestTransactionDTO);
    }

    @RequestMapping(value = "/run", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> runPayment(@Valid @RequestBody RequestApplyTransactionDTO requestApplyTransactionDTO, @RequestHeader HttpHeaders headers) {

        return paymentServiceInterface.applyTransaction(requestApplyTransactionDTO);


    }

}
