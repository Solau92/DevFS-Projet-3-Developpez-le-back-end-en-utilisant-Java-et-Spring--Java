package com.ChaTop.Rental.DTO;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RentalRegisterDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String surface;

    @NotBlank
    private String price;

    @NotNull
    private MultipartFile picture;

    @NotBlank
    private String description;

    private String ownerEmail;

    public RentalRegisterDTO(String name, String surface, String price, MultipartFile picture, String description,
            String ownerEmail) {
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

    public void setOwner_Email(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    @Override
    public String toString() {
        return "{" +
                " name='" + getName() + "'" +
                ", surface='" + getSurface() + "'" +
                ", price='" + getPrice() + "'" +
                ", picture='" + getPicture() + "'" +
                ", description='" + getDescription() + "'" +
                ", ownerEmail='" + getOwnerEmail() + "'" +
                "}";
    }

}
