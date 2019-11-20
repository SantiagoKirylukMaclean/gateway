package ar.tesis.gateway.controller;

import ar.tesis.gateway.model.Payment;
import ar.tesis.gateway.model.Transaction;
import ar.tesis.gateway.modelDTO.RequestTransactionDTO;
import ar.tesis.gateway.modelDTO.TransactionDTO;
import ar.tesis.gateway.repository.UserRepository;
import ar.tesis.gateway.service.PaymentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public RequestTransactionDTO startPayment(@Valid @RequestBody RequestTransactionDTO RequestTransactionDTO, @RequestHeader HttpHeaders headers) {

        paymentServiceInterface.validTransaction(RequestTransactionDTO);

        return RequestTransactionDTO;
    }

    @RequestMapping(value = "/run", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public String runPayment(@Valid @RequestBody RequestTransactionDTO RequestTransactionDTO, @RequestHeader HttpHeaders headers) {

        paymentServiceInterface.validTransaction(RequestTransactionDTO);

        return RequestTransactionDTO.getMailComprador();
    }

}
