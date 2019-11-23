package ar.tesis.gateway.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.tesis.gateway.model.Payment;
import ar.tesis.gateway.modelDTO.RequestApplyTransactionDTO;
import ar.tesis.gateway.modelDTO.RequestTransactionDTO;
import ar.tesis.gateway.repository.UserRepository;
import ar.tesis.gateway.service.PaymentServiceInterface;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentServiceInterface paymentServiceInterface;

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String applyPayment(@RequestBody Payment payment, @RequestHeader HttpHeaders headers) {

        return userRepository.findByUsername(payment.getSeller()).toString();
    }

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
