package com.ChaTop.Rental.service;

import java.io.IOException;
import java.util.List;

import com.ChaTop.Rental.DTO.RentalDTO;
import com.ChaTop.Rental.DTO.RentalDTOPicture;
import com.ChaTop.Rental.DTO.RentalRegisterDTO;
import com.ChaTop.Rental.DTO.RentalUpdateDTO;
import com.ChaTop.Rental.exception.UserNotFoundException;

public interface RentalsService {

    public List<RentalDTOPicture> getAllRentals();

    public RentalDTO findById(int id);

    public void saveRental(RentalRegisterDTO rentalRegisterDTO) throws UserNotFoundException, IOException;

    public void updateRental(RentalUpdateDTO rentalUpdateDTO);
}
