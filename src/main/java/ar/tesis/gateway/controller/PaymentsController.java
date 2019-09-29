package ar.tesis.gateway.controller;

import ar.tesis.gateway.model.Payment;
import ar.tesis.gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String applyPayment(@RequestBody Payment payment, @RequestHeader HttpHeaders headers) {

        return userRepository.findByUsername(payment.getSeller()).toString();
    }

}
