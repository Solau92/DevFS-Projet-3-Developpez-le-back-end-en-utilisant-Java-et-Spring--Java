package com.ChaTop.Rental.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ChaTop.Rental.DTO.RentalDTO;
import com.ChaTop.Rental.DTO.RentalRegisterDTO;
import com.ChaTop.Rental.DTO.RentalUpdateDTO;
import com.ChaTop.Rental.DTO.response.RentalAddResponse;
import com.ChaTop.Rental.DTO.response.RentalUpdateResponse;
import com.ChaTop.Rental.DTO.response.RentalsResponse;
import com.ChaTop.Rental.exception.RentalNotFoundException;
import com.ChaTop.Rental.exception.UserNotFoundException;
import com.ChaTop.Rental.service.RentalsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rentals")
@SecurityRequirement(name = "Bearer Authentication")
public class RentalsController {

    private RentalsService rentalsService;

    private static final Logger log = LoggerFactory.getLogger(RentalsController.class);

    @Value("${picture-upload-directory}")
    private String uploadDir;

    @Value("${picture-upload-directory-path}")
    private String uploadDirPath;

    public RentalsController(RentalsService rentalsService) {
        this.rentalsService = rentalsService;
    }

    /**
     * 
     * @return
     */
    @Operation(summary = "All rentals", description = "Get all the rentals")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = RentalsResponse.class), mediaType = "application/json") }, description = "List of rentals successfully obtained"),
            @ApiResponse(responseCode = "401", content = {
                    @Content(schema = @Schema()) }, description = "Unauthorize user")
    })
    @GetMapping("")
    public ResponseEntity<RentalsResponse> getAllRentals() {
        log.info("/rentals : Getting the list of all rentals");
        return ResponseEntity.status(HttpStatus.OK).body(new RentalsResponse(rentalsService.getAllRentals()));
    }

    /**
     * 
     * @param id
     * @return
 * @throws RentalNotFoundException 
     */
    @Operation(summary = "Getting a rental", description = "Getting a rental, given its id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = RentalDTO.class), mediaType = "application/json") }, description = "Rental successfully obtained"),
            @ApiResponse(responseCode = "401", content = {
                    @Content(schema = @Schema()) }, description = "Unauthorize user")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRental(@PathVariable int id) throws RentalNotFoundException {
        log.info("/rentals/{} : Searching rental with id {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(rentalsService.findById(id));
    }

    /**
     * 
     * @param authentication
     * @param rentalRegisterDTO
     * @return
     * @throws UserNotFoundException
     * @throws IOException
 * @throws RentalNotFoundException 
     */
    @Operation(summary = "Saving a new rental", description = "Saving the given new rental")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = RentalAddResponse.class), mediaType = "application/json") }, description = "Rental successfully saved"),
            @ApiResponse(responseCode = "401", content = {
                    @Content(schema = @Schema()) }, description = "Unauthorize user")
    })
    @PostMapping("")
    public ResponseEntity<RentalAddResponse> addRental(Authentication authentication,
            @Valid @ModelAttribute("rentalDTO") RentalRegisterDTO rentalRegisterDTO)
            throws UserNotFoundException, IOException, RentalNotFoundException {

        // Pour récup (in fine) le owner_id
        String ownerEmail = authentication.getName();
        rentalRegisterDTO.setOwner_Email(ownerEmail);

        rentalsService.saveRental(rentalRegisterDTO);

        RentalAddResponse response = new RentalAddResponse();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 
     * @param id
     * @param rentalUpdateDTO
     * @return
 * @throws RentalNotFoundException 
     */
    @Operation(summary = "Updating a rental", description = "Updating a rental")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = RentalUpdateResponse.class), mediaType = "application/json") }, description = "Rental successfully updated"),
            @ApiResponse(responseCode = "401", content = {
                    @Content(schema = @Schema()) }, description = "Unauthorize user")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RentalUpdateResponse> updateRental(@PathVariable int id, @Valid @ModelAttribute("rentalDTO") RentalUpdateDTO rentalUpdateDTO) throws RentalNotFoundException {

        rentalUpdateDTO.setId(id);
        rentalsService.updateRental(rentalUpdateDTO);

        RentalUpdateResponse response = new RentalUpdateResponse();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    @Operation(description = "Method used to display pictures", hidden = true)
    @GetMapping("/rental-pictures/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws FileNotFoundException {

        Path uploadPath = Paths.get(uploadDirPath + uploadDir + '/' + fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(uploadPath.toUri());
        } catch (MalformedURLException ex) {
            log.error("Error --------");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .body(resource);
    }

}