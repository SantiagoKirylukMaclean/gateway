package ar.tesis.gateway.controller;

import ar.tesis.gateway.model.Transaction;
import ar.tesis.gateway.security.jwt.JwtProvider;
import ar.tesis.gateway.service.TransactionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/seller")

public class SellerController {

    @Autowired
    TransactionServiceInterface transactionServiceInterface;

    @Autowired
    JwtProvider jwtProvider;

    @RequestMapping(value = "/incomplete", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public List<Transaction> getIncompleteTransactions(HttpServletRequest headers) {
        String authHeader = headers.getHeader("Authorization");
        String user = jwtProvider.getUserNameFromJwtToken(authHeader.replace("Bearer ",""));
        return transactionServiceInterface.findIncompleteTrnsactions(user);
    }

}
