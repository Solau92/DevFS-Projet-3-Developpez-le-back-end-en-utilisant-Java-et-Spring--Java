package com.ChaTop.Rental.DTO;

import jakarta.validation.constraints.NotBlank;

public class RentalUpdateDTO {

    private int id;

    @NotBlank
    private String name;
    
    @NotBlank
    private String surface;

    @NotBlank
    private String price;

    @NotBlank
    private String description;

    public RentalUpdateDTO(int id, String name, String surface, String price, String description) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    
}
