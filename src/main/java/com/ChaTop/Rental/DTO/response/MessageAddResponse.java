package com.ChaTop.Rental.DTO.response;

public class MessageAddResponse {

    String message;

    public MessageAddResponse() {
        this.message = "Message send with success";
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
