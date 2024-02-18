package com.ChaTop.Rental.service.Implementation;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ChaTop.Rental.DTO.MessageRegisterDTO;
import com.ChaTop.Rental.entity.Message;
import com.ChaTop.Rental.exception.ErrorSavingMessageException;
import com.ChaTop.Rental.repository.MessagesRepository;
import com.ChaTop.Rental.service.MessagesService;

@Service
public class MessagesServiceImpl implements MessagesService {

    private MessagesRepository messagesRepository;

    private static final Logger log = LoggerFactory.getLogger(MessagesServiceImpl.class);

    public MessagesServiceImpl(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    // TODO question : Return void ?
    @Override
    public void saveMessage(MessageRegisterDTO messageRegisterDTO) throws ErrorSavingMessageException {

        log.info("Trying to save message : {}", messageRegisterDTO);

        // TODO question : gérer erreurs, mais quand renvoyer erreur 400 ? 
        boolean error = false;

        if(error) {
            log.info("Error saving message");
            throw new ErrorSavingMessageException("Error when saving message");
        }

        Message messageToSave = new Message(messageRegisterDTO.getRental_id(), messageRegisterDTO.getUser_id(), messageRegisterDTO.getMessage(), LocalDate.now());

        messagesRepository.save(messageToSave);

    } 
}
