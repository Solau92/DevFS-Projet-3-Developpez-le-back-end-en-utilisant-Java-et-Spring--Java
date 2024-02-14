package com.ChaTop.Rental.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ChaTop.Rental.entity.User;
import com.ChaTop.Rental.exception.UserNotFoundException;
import com.ChaTop.Rental.service.UsersService;

@RestController
@RequestMapping("/api/user")
public class UsersController {

    private UsersService usersService;

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
    
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/{id}") // Renvoyer DTO ??????
    public ResponseEntity<User> getUser(@PathVariable int id) throws UserNotFoundException {
        log.info("Searching user with id {}", id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usersService.findById(id));
    }

}
