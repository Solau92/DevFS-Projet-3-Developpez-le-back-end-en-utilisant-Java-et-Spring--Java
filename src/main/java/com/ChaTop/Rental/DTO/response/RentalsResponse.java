package com.ChaTop.Rental.DTO.response;

import java.util.List;

import com.ChaTop.Rental.DTO.RentalDTOPicture;

public class RentalsResponse {
    
    public List<RentalDTOPicture> rentals; 

        public RentalsResponse(List<RentalDTOPicture> rentals) {
            this.rentals = rentals;
        }
}
