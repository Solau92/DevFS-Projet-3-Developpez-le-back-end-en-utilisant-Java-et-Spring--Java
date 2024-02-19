package com.ChaTop.Rental.DTO.response;

public class RentalUpdateResponse {
    
    String message;

    public RentalUpdateResponse() {
        this.message = "Rental updated !";
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
