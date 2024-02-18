package com.ChaTop.Rental.DTO;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

public class RentalRegisterDTO {

    private String name;
    private String surface;
    private String price;
    private MultipartFile picture;
    private String description;
    private String ownerEmail;
    // private LocalDate created_at;
    // private LocalDate updated_at;

    public RentalRegisterDTO(String name, String surface, String price, MultipartFile picture, String description, String ownerEmail) {
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.ownerEmail = ownerEmail;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurface() {
        return this.surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public MultipartFile getPicture() {
        return this.picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerEmail() {
        return this.ownerEmail;
    }

    public void setOwner_id(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    // public int getOwner_id() {
    //     return this.owner_id;
    // }

    // public void setOwner_id(int owner_id) {
    //     this.owner_id = owner_id;
    // }

    // public LocalDate getCreated_at() {
    //     return this.created_at;
    // }

    // public void setCreated_at(LocalDate created_at) {
    //     this.created_at = created_at;
    // }

    // public LocalDate getUpdated_at() {
    //     return this.updated_at;
    // }

    // public void setUpdated_at(LocalDate updated_at) {
    //     this.updated_at = updated_at;
    // }
    
}
