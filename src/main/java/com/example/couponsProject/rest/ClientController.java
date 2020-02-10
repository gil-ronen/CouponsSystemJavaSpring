package com.example.couponsProject.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public abstract class ClientController {
    /**
     * login to client user
     * @param email    String
     * @param password String
     * @return ResponseEntity<>
     */
    public abstract ResponseEntity<?> login(String email, String password);
}
