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

import com.ChaTop.Rental.DTO.UserLoginDTO;
import com.ChaTop.Rental.DTO.UserRegisterDTO;
import com.ChaTop.Rental.DTO.response.LoginResponse;
import com.ChaTop.Rental.DTO.response.MeResponse;
import com.ChaTop.Rental.exception.BadCredentialsCustomException;
import com.ChaTop.Rental.exception.UserAlreadyExistsException;
import com.ChaTop.Rental.service.JWTService;
import com.ChaTop.Rental.service.UsersService;
import com.nimbusds.jose.shaded.gson.Gson;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private JWTService jwtService;
    private UsersService usersService;

    private static final Logger log = LoggerFactory.getLogger(JWTService.class);

    private Gson gson = new Gson();

    public AuthenticationController(JWTService jwtService, UsersService usersService) {
        this.jwtService = jwtService;
        this.usersService = usersService;
    }

    @PostMapping(value="/login", produces = "application/json")
    public ResponseEntity<String> getToken(@RequestBody UserLoginDTO userLoginDto) throws BadCredentialsCustomException {
        
        // TODO vérifier user + mdp ou ERREUR
        // Si user et mdp ok -> renvoyer token 
        // Sinon : 401, {"message": "error"}

        // Intérêt de récupérer le User renvoyé ? 
        usersService.authorizedUser(userLoginDto);

        String token = jwtService.generateToken(userLoginDto);
        LoginResponse response = new LoginResponse(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(gson.toJson(response));
    }

    @GetMapping(value="/me", produces = "application/json")
    public ResponseEntity<String> me(Authentication authentication) {
        log.info(authentication.toString());
        // TODO UserService pour récuperer les infos du user
        MeResponse response = new MeResponse("1", "a@a.com", "a@a.com", "2024/02/13", "2024/02/13");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(gson.toJson(response));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterDTO userRegisterDTO) throws UserAlreadyExistsException {
        
        log.info("api/register Create user : " + userRegisterDTO.toString());
        
        usersService.saveUser(userRegisterDTO);
        
        UserLoginDTO userLoginDTO = new UserLoginDTO(userRegisterDTO.getEmail(), userRegisterDTO.getPassword());
        String token = jwtService.generateToken(userLoginDTO);
        LoginResponse response = new LoginResponse(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(gson.toJson(response));
    }

}
