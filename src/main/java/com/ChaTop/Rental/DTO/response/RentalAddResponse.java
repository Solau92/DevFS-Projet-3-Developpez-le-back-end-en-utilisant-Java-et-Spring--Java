package com.ChaTop.Rental.DTO.response;

public class RentalAddResponse {

    String message;

    public RentalAddResponse() {
        this.message = "Rental created !";
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
