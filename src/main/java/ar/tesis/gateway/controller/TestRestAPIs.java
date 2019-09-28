package ar.tesis.gateway.controller;

import ar.tesis.gateway.model.User;
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

    @RequestMapping(value = "/api/test/user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        return ">>> User Contents!";
    }

    @GetMapping("/api/test/pm")
    @PreAuthorize("hasRole('PM')")
    public String projectManagementAccess() {
        return ">>> Board Management Project";
    }

    @GetMapping("/api/test/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return ">>> Admin Contents";
    }

    @RequestMapping(value = "/api/test/users", method = RequestMethod.GET)
    public List<User> findAllUsers() {

        return userRepository.findAll();
    }
}