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
import com.ChaTop.Rental.entity.Rental;
import com.ChaTop.Rental.service.MessagesService;
import com.nimbusds.jose.shaded.gson.Gson;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.media.Content;
// import io.swagger.v3.oas.annotations.media.Schema;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/messages")

public class MessagesController {

    private MessagesService messagesService;

    private static final Logger log = LoggerFactory.getLogger(MessagesController.class);

    private Gson gson = new Gson();

    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping("")
    public ResponseEntity<String> addMessage(@RequestBody MessageRegisterDTO messageRegisterDTO) {

        log.info("api/messages : Create message : {}", messageRegisterDTO.toString());
        messagesService.saveMessage(messageRegisterDTO);

        MessageAddResponse response = new MessageAddResponse();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(gson.toJson(response));
    }

    
}
