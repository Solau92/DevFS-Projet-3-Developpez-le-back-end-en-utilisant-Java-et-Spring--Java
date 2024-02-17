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
import com.ChaTop.Rental.entity.User;
import com.ChaTop.Rental.exception.BadCredentialsCustomException;
import com.ChaTop.Rental.exception.UserAlreadyExistsException;
import com.ChaTop.Rental.exception.UserNotFoundException;
import com.ChaTop.Rental.service.JWTService;
import com.ChaTop.Rental.service.UsersService;
import com.nimbusds.jose.shaded.gson.Gson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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

    @Operation(summary = "Login", description = "Login process")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = LoginResponse.class), mediaType = "application/json") }, description = "User successfully loged in"),
        @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}, description = "Unauthorize user")
    })
    @PostMapping(value="/login", produces = "application/json")
    public ResponseEntity<String> getToken(@RequestBody UserLoginDTO userLoginDto) throws BadCredentialsCustomException {

        // Intérêt de récupérer le User renvoyé ? 
        usersService.validateCredentials(userLoginDto);

        String token = jwtService.generateToken(userLoginDto);
        LoginResponse response = new LoginResponse(token);
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(response));
    }

    @Operation(summary = "Getting user information", description = "Getting logged user information")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = MeResponse.class), mediaType = "application/json") }, description = "Information of user successfully obtained"),
        @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}, description = "Unauthorize user")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value="/me", produces = "application/json")
    public ResponseEntity<String> me(Authentication authentication) throws UserNotFoundException {

        String email = authentication.getName();

        User user = usersService.findByEmail(email);

        MeResponse response = new MeResponse(String.valueOf(user.getId()), user.getName(), user.getEmail(), user.getCreated_at().toString(), user.getUpdated_at() == null ? null : user.getUpdated_at().toString());
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(response));
    }

    @Operation(summary = "Register", description = "Register process")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }, description = "User successfully registred"),
        @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}, description = "Unauthorize user")
    })
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterDTO userRegisterDTO) throws UserAlreadyExistsException {
        
        log.info("api/register Create user : {}", userRegisterDTO.toString());
        
        usersService.saveUser(userRegisterDTO);
        
        UserLoginDTO userLoginDTO = new UserLoginDTO(userRegisterDTO.getEmail(), userRegisterDTO.getPassword());
        String token = jwtService.generateToken(userLoginDTO);
        LoginResponse response = new LoginResponse(token);
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(response));
    }

}
