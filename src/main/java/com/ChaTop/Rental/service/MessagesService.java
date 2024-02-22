package com.ChaTop.Rental.service;

import com.ChaTop.Rental.DTO.MessageRegisterDTO;

public interface MessagesService {

    /**
     * Saves the message.
     * 
     * @param messageRegisterDTO
     */
    void saveMessage(MessageRegisterDTO messageRegisterDTO);

}
