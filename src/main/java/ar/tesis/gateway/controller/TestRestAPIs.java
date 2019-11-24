package ar.tesis.gateway.controller;

import ar.tesis.gateway.model.Seller;
import ar.tesis.gateway.model.User;
import ar.tesis.gateway.repository.SellerRepository;
import ar.tesis.gateway.repository.UserRepository;
import ar.tesis.gateway.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TestRestAPIs {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    JwtProvider jwtProvider;

    @RequestMapping(value = "/api/test/user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userAccess() {
        return ">>> User Contents!";
    }

    @GetMapping("/api/test/pm")
    @PreAuthorize("hasRole('ROLE_PM')")
    public String projectManagementAccess() {
        return ">>> Board Management Project";
    }

    @GetMapping("/api/test/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAccess() {
        return ">>> Admin Contents";
    }

    @RequestMapping(value = "/api/test/users", method = RequestMethod.GET)
    public List<User> findAllUsers() {

        return userRepository.findAll();
    }

    @RequestMapping(value = "/api/test/seller", method = RequestMethod.GET)
    public List<Seller> findAllSeller(HttpServletRequest headers) {
        String authHeader = headers.getHeader("Authorization");
        String user = jwtProvider.getUserNameFromJwtToken(authHeader.replace("Bearer ",""));
        return sellerRepository.findAll();
    }
}