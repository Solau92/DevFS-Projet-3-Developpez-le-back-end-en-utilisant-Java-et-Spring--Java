package com.ChaTop.Rental.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
import com.ChaTop.Rental.exception.UserNotFoundException;
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

    @Value("${picture-upload-directory}")
    private String uploadDir;

    @Value("${picture-upload-directory-path}")
    private String uploadDirPath;

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

    // Not working properly, TODO
    @Operation(summary = "Saving a new rental", description = "Saving the given new rental")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }, description = "Rental successfully saved"),
        @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}, description = "Unauthorize user")
    }) 
    @PostMapping("")
    public ResponseEntity<String> addRental(Authentication authentication, @RequestParam("name") String name,
            @RequestParam("surface") String surface, @RequestParam("price") String price,
            @RequestParam("description") String description, @RequestParam("picture") MultipartFile pictureFile) throws UserNotFoundException, IOException {

        // Pour récup (in fine) le owner_id
        String ownerEmail = authentication.getName();

        RentalRegisterDTO rentalRegisterDTO = new RentalRegisterDTO(name, surface, price, pictureFile, description, ownerEmail);
        rentalsService.saveRental(rentalRegisterDTO);

        RentalAddResponse response = new RentalAddResponse();
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(response));
    }

       @Operation(summary = "Updating a rental", description = "Updating a rental")
       @ApiResponses({
           @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }, description = "Rental successfully updated"),
           @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema())}, description = "Unauthorize user")
       }) 
       @PutMapping("/{id}")
       public ResponseEntity<String> updateRental(@PathVariable int id, @RequestParam("name") String name,
       @RequestParam("surface") String surface, @RequestParam("price") String price,
       @RequestParam("description") String description) {

        RentalUpdateDTO rentalUpdateDTO = new RentalUpdateDTO(id, name, surface, price, description);
        rentalsService.updateRental(rentalUpdateDTO);

        RentalUpdateResponse response = new RentalUpdateResponse();
        
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(response));
       }

       @GetMapping("/rental-pictures/{fileName:.+}")
        public ResponseEntity <Resource> downloadFile(@PathVariable String fileName) throws FileNotFoundException {
        // FileMetaData fileData = fileStorageService.getFile(fileName);

        //String uploadDir = "rental-pictures";
        // Path uploadPath = Paths.get("src/main/resources/static/" + uploadDir);
        
        Path uploadPath = Paths.get(uploadDirPath + uploadDir + '/' + fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(uploadPath.toUri());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileName + "\"")
            .contentType(MediaType.parseMediaType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
            .body(resource);
    }

}