package com.ChaTop.Rental.DTO.response;

import java.util.List;

import com.ChaTop.Rental.DTO.RentalDTOPicture;

public class RentalsResponse {

    private List<RentalDTOPicture> rentals;

    public RentalsResponse(List<RentalDTOPicture> rentals) {
        this.rentals = rentals;
    }

    public List<RentalDTOPicture> getRentals() {
        return this.rentals;
    }

    public void setRentals(List<RentalDTOPicture> rentals) {
        this.rentals = rentals;
    }

}
