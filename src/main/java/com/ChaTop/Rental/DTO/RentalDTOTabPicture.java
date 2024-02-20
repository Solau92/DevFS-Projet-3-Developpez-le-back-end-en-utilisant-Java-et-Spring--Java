package com.ChaTop.Rental.DTO;

import java.time.LocalDate;

public class RentalDTOTabPicture extends RentalDTO {

    private String[] picture;

    public RentalDTOTabPicture() {
    }

    public RentalDTOTabPicture(int id, String name, double surface, double price, String description, int owner_id, LocalDate created_at, LocalDate updated_at, String[] picture) {
        super(id, name, surface, price, description, owner_id, created_at, updated_at);
        this.picture = picture;
    }

    public String[] getPicture() {
        return this.picture;
    }

    public void setPicture(String[] picture) {
        this.picture = picture;
    }

    public void setPictureFromPicture(String pictureFile) {
        this.picture = null;
    }
    
}
