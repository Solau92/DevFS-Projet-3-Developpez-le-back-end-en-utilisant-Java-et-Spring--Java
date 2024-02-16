package com.ChaTop.Rental.service;

import java.util.List;

import com.ChaTop.Rental.DTO.RentalDTO;
import com.ChaTop.Rental.DTO.RentalRegisterDTO;
import com.ChaTop.Rental.entity.Rental;

public interface RentalsService {

    public List<Rental> getAllRentals();

    public RentalDTO findById(int id);

    public Rental saveRental(RentalRegisterDTO rentalRegisterDTO);
}
