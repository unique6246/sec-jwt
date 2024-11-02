package com.example.demo.controller;
import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
public class AccountController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping("/users")
    public List<User> getUser() {
        return accountService.getUsers();
    }

    @PostMapping("/register")
    public User addUser(@RequestBody User user) {
        return accountService.addUser(user);
    }

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid username or password", e);
        }

        final UserDetails userDetails = accountService.loadUserByUsername(authRequest.getUsername());

        return jwtUtil.generateToken(userDetails);
    }
}
