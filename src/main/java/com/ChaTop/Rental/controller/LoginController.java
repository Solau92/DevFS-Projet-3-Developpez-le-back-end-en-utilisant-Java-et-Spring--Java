package com.ChaTop.Rental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ChaTop.Rental.service.JWTService;

@RestController
public class LoginController {

    private JWTService jwtService;

    public LoginController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/api/auth/login")
    public String getToken(Authentication authentication) {
        String token = jwtService.generateToken(authentication);
        return token;
    }



    // @PostMapping("/api/auth/login")
    // public ResponseEntity<String> login(@RequestBody UserDto userDto) {
    // log.info("/api/login User : " + userDto.getEmail());
    // return ResponseEntity.status(HttpStatus.ACCEPTED).body(jwtService.generateToken(authentication));
    // }

}
