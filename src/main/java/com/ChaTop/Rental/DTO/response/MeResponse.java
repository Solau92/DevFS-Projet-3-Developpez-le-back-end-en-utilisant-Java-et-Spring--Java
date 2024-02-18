package com.ChaTop.Rental.DTO.response;

public class MeResponse {

    int id;
    String name;
    String email;
    String created_at;
    String updated_at;

    public MeResponse(int id, String name, String email, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
    
}
