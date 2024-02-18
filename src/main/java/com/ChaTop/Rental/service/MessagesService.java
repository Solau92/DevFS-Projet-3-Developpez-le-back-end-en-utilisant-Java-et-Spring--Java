package com.ChaTop.Rental.service;

import com.ChaTop.Rental.DTO.MessageRegisterDTO;
import com.ChaTop.Rental.exception.ErrorSavingMessageException;

public interface MessagesService {

    public void saveMessage(MessageRegisterDTO messageRegisterDTO) throws ErrorSavingMessageException;
    
}
