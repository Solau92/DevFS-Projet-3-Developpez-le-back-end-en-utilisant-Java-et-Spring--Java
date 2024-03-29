package com.ChaTop.Rental.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ChaTop.Rental.DTO.UserDTO;
import com.ChaTop.Rental.exception.UserNotFoundException;
import com.ChaTop.Rental.service.UsersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "Bearer Authentication")
public class UsersController {

    private UsersService usersService;

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * Getting a user, given his id. 
     * @param id
     * @return ResponseEntity<UserDTO>, with status OK
     * @throws UserNotFoundException if the user was not found
     */
    @Operation(summary = "Getting a user", description = "Getting a user, given his id")
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }, description = "User successfully obtained")
    @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema()) }, description = "Unauthorize user")

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) throws UserNotFoundException {

        log.info("Searching user with id {}", id);

        return ResponseEntity.status(HttpStatus.OK).body(usersService.findById(id));
    }

}
