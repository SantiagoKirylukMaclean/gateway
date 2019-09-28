package ar.tesis.gateway.controller;

import ar.tesis.gateway.model.Payment;
import ar.tesis.gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public String authenticateUser(@RequestBody Payment payment) {

        return userRepository.findByUsername(payment.getSeller()).toString();
    }

}
