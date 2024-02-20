package com.ChaTop.Rental.service.Implementation;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
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
    public void saveMessage(MessageRegisterDTO messageRegisterDTO) {

        log.info("Trying to save message : {}", messageRegisterDTO);

        ModelMapper mapper = new ModelMapper();
        Message messageToSave = mapper.map(messageRegisterDTO, Message.class);
        messageToSave.setCreated_at(LocalDate.now());
        messageToSave.setUpdated_at(LocalDate.now());

        messagesRepository.save(messageToSave);

    } 
}
