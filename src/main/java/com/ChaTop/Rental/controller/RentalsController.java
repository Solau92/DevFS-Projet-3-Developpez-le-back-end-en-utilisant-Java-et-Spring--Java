package com.ChaTop.Rental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ChaTop.Rental.DTO.RentalDTO;
import com.ChaTop.Rental.DTO.response.RentalAddResponse;
import com.ChaTop.Rental.DTO.response.RentalsResponse;
import com.ChaTop.Rental.entity.Rental;
import com.ChaTop.Rental.service.RentalsService;
import com.nimbusds.jose.shaded.gson.Gson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/rentals")
@SecurityRequirement(name = "Bearer Authentication")
public class RentalsController {

    private RentalsService rentalsService;

    private static final Logger log = LoggerFactory.getLogger(RentalsController.class);

    private Gson gson = new Gson();

    public RentalsController(RentalsService rentalsService) {
        this.rentalsService = rentalsService;
    }

    @Operation(summary = "All rentals", description = "Get all the rentals")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RentalsResponse.class), mediaType = "application/json") }, description = "List of rentals successfully obtained"),
        @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema()) }, description = "Unauthorize user")
    })
    @GetMapping("")
    public ResponseEntity<RentalsResponse> getAllRentals() {
        log.info("/rentals : Getting the list of all rentals");
        return ResponseEntity.status(HttpStatus.OK).body(new RentalsResponse(rentalsService.getAllRentals()));
    }

    @Operation(summary = "Getting a rental", description = "Getting a rental, given its id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = RentalDTO.class), mediaType = "application/json") }, description = "Rental successfully obtained"),
        @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}, description = "Unauthorize user")
    }) 
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRental(@PathVariable int id) {
        log.info("/rentals/{} : Searching rental with id {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(rentalsService.findById(id));
    }

    // Not working, TODO
    @Operation(summary = "Saving a new rental", description = "Saving the given new rental")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }, description = "Rental successfully saved"),
        @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}, description = "Unauthorize user")
    }) 
    @PostMapping("")
    // public ResponseEntity<String> addRental(@ModelAttribute("formData")
    // RentalRegisterDTO rentalRegisterDTO) {
    public ResponseEntity<String> addRental(@RequestParam("name") String name,
            @RequestParam("surface") String surface, @RequestParam("price") String price,
            @RequestParam("description") String description, @RequestParam("picture") MultipartFile file) {

        log.info("name : {} ", name);
        log.info("picture : {}", file.toString());

        // rentalRegisterDTO.setPicture("https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg");
        // log.info("/rentals : Create rental {}", rentalRegisterDTO.toString());

        // rentalsService.saveRental(rentalRegisterDTO);
        RentalAddResponse response = new RentalAddResponse();
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(response));
    }

       // TODO
       @Operation(summary = "Updating a rental", description = "Updating a rental")
       @ApiResponses({
           @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }, description = "Rental successfully updated"),
           @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}, description = "Unauthorize user")
       }) 
       @PutMapping("")
       public ResponseEntity<String> updateRental() {
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(""));
       }

}