package com.ChaTop.Rental.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ChaTop.Rental.entity.User;
import com.ChaTop.Rental.service.UsersService;

@RestController
public class UsersController {

    private UsersService usersService;

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
    
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws Exception {
        log.info("api/register Create user : " + user.toString());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usersService.saveUser(user));
    }

}
