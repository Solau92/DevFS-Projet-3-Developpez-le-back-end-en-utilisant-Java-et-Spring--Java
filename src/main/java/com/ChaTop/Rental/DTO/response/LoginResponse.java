package com.ChaTop.Rental.DTO.response;

public class LoginResponse {

    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public LoginResponse() {
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
  
}
