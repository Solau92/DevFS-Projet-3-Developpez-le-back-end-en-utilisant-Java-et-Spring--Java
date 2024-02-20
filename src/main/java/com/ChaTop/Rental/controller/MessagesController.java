package com.ChaTop.Rental.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ChaTop.Rental.DTO.MessageRegisterDTO;
import com.ChaTop.Rental.DTO.response.MessageAddResponse;
import com.ChaTop.Rental.service.MessagesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/messages")
@SecurityRequirement(name = "Bearer Authentication")
public class MessagesController {

    private MessagesService messagesService;

    private static final Logger log = LoggerFactory.getLogger(MessagesController.class);

    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @Operation(summary = "Saving a message", description = "Saves the given message, and return a success message")
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MessageAddResponse.class), mediaType = "application/json") }, description = "Message successfully saved")
    @ApiResponse(responseCode = "400", content = {
            @Content(schema = @Schema()) }, description = "Error when saving message")
    @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema()) }, description = "Unauthorize user")

    @PostMapping("")
    public ResponseEntity<MessageAddResponse> addMessage(@Valid @RequestBody MessageRegisterDTO messageRegisterDTO) {

        log.info("api/messages : trying to create message : {}", messageRegisterDTO);
        
        messagesService.saveMessage(messageRegisterDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new MessageAddResponse());
    }

}
