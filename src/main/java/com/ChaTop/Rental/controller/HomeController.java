package com.ChaTop.Rental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ChaTop.Rental.entity.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class HomeController {

    @Operation(summary = "Home page", description = "Home / default page")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }, description = "Home page successfully displayed"),
    })      
    @GetMapping("/")
    public ResponseEntity<String> getHomePage() {
        return ResponseEntity.status(HttpStatus.OK).body("HomePage");
    }
    
}
