package com.ChaTop.Rental.DTO.response;

import java.util.List;

import com.ChaTop.Rental.entity.Rental;

public class RentalsResponse {
    
    public List<Rental> rentals; 

        public RentalsResponse(List<Rental> rentals) {
            this.rentals = rentals;
        }
}
