package com.ChaTop.Rental.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ChaTop.Rental.DTO.UserDto;
import com.ChaTop.Rental.DTO.response.LoginResponse;
import com.ChaTop.Rental.DTO.response.MeResponse;
import com.ChaTop.Rental.service.JWTService;
import com.nimbusds.jose.shaded.gson.Gson;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private JWTService jwtService;

    private static final Logger log = LoggerFactory.getLogger(JWTService.class);

    private Gson gson = new Gson();

    public AuthenticationController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping(value="/login", produces = "application/json")
    public ResponseEntity<String> getToken(@RequestBody UserDto userDto) {
        // TODO vérifier user + mdp ou ERREUR
        String token = jwtService.generateToken(userDto);
        LoginResponse response = new LoginResponse(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(gson.toJson(response));
    }

    @GetMapping(value="/me", produces = "application/json")
    public ResponseEntity<String> me(Authentication authentication) {
        // TODO UserService pour récuperer les infos du user
        MeResponse response = new MeResponse("1", "a@a.com", "a@a.com", "2024/02/13", "2024/02/13");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(gson.toJson(response));
    }

}
