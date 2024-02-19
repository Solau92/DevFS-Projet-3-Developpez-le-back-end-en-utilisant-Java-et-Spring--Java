package com.ChaTop.Rental.DTO;

import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {

    @NotBlank
    private String email;
    
    @NotBlank
    private String password;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }


}
