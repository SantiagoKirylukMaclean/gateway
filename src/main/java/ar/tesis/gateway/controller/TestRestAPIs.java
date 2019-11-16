package ar.tesis.gateway.controller;

import ar.tesis.gateway.model.Seller;
import ar.tesis.gateway.model.User;
import ar.tesis.gateway.repository.SellerRepository;
import ar.tesis.gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestRestAPIs {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;

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
    public List<Seller> findAllSeller() {

        return sellerRepository.findAll();
    }
}