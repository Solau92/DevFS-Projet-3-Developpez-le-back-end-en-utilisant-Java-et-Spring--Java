package com.ChaTop.Rental.service.Implementation;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ChaTop.Rental.DTO.MessageRegisterDTO;
import com.ChaTop.Rental.entity.Message;
import com.ChaTop.Rental.repository.MessagesRepository;
import com.ChaTop.Rental.service.MessagesService;

@Service
public class MessagesServiceImpl implements MessagesService {

    private MessagesRepository messagesRepository;

    private static final Logger log = LoggerFactory.getLogger(MessagesServiceImpl.class);

    public MessagesServiceImpl(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @Override
    public Message saveMessage(MessageRegisterDTO messageRegisterDTO) {

        log.info("Trying to save message : {}", messageRegisterDTO);

        // Gérer erreurs ? TODO : renvoyer erreur 400

        Message messageToSave = new Message(messageRegisterDTO.getRental_id(), messageRegisterDTO.getUser_id(), messageRegisterDTO.getMessage(), LocalDate.now());
        log.info("Trying to save message : {}", messageToSave);
        // void ? 
        return messagesRepository.save(messageToSave);
    } 
}