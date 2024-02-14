package com.ChaTop.Rental.DTO;

import java.time.LocalDate;

public class UserDTO {

    private int id;
    private String email;
    private String name;
    private String password;
    private LocalDate created_at;
    private LocalDate updated_at;


    public UserDTO(int id, String email, String name, String password, LocalDate created_at, LocalDate updated_at) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public LocalDate getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }
    
}
