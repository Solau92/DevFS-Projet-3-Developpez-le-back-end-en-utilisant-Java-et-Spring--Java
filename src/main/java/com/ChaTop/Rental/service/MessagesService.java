package com.ChaTop.Rental.service;

import com.ChaTop.Rental.DTO.MessageRegisterDTO;
import com.ChaTop.Rental.entity.Message;
import com.ChaTop.Rental.exception.ErrorSavingMessageException;

public interface MessagesService {

    Message saveMessage(MessageRegisterDTO messageRegisterDTO) throws ErrorSavingMessageException;
    
}
