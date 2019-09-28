package ar.tesis.gateway.controller;

import ar.tesis.gateway.model.JwtResponse;
import ar.tesis.gateway.model.LoginForm;
import ar.tesis.gateway.model.Payment;
import ar.tesis.gateway.model.User;
import ar.tesis.gateway.repository.UserRepository;
import ar.tesis.gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;


    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        String jwt = authService.authUser(loginRequest);
        return ResponseEntity.ok(new JwtResponse(jwt, "Bearer"));
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<?> generateTokenClient(@RequestBody Payment payment) {

        return ResponseEntity.ok(new JwtResponse(authService.generateTokenClient(payment), "Bearer"));
    }

}
