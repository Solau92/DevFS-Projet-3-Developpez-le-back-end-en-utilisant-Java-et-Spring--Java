package com.ChaTop.Rental.service;

import com.ChaTop.Rental.DTO.MessageRegisterDTO;
import com.ChaTop.Rental.entity.Message;

public interface MessagesService {

    Message saveMessage(MessageRegisterDTO messageRegisterDTO);
    
}
