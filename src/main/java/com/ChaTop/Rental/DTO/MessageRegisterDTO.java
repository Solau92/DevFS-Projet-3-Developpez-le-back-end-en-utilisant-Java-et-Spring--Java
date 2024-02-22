package com.ChaTop.Rental.DTO;

import jakarta.validation.constraints.NotBlank;

public class MessageRegisterDTO {

    @NotBlank
    private String message;
    
    private int user_id;
    private int rental_id;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRental_id() {
        return this.rental_id;
    }

    public void setRental_id(int rental_id) {
        this.rental_id = rental_id;
    }


    @Override
    public String toString() {
        return "{" +
            " message='" + getMessage() + "'" +
            ", user_id='" + getUser_id() + "'" +
            ", rental_id='" + getRental_id() + "'" +
            "}";
    }

    
}
