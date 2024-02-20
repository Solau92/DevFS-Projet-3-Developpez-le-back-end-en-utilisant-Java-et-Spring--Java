package com.ChaTop.Rental.service;

import java.io.IOException;
import java.util.List;

import com.ChaTop.Rental.DTO.RentalDTO;
import com.ChaTop.Rental.DTO.RentalDTOPicture;
import com.ChaTop.Rental.DTO.RentalRegisterDTO;
import com.ChaTop.Rental.DTO.RentalUpdateDTO;
import com.ChaTop.Rental.exception.RentalNotFoundException;
import com.ChaTop.Rental.exception.UserNotFoundException;

public interface RentalsService {

    /**
     * Returns a list of all rentals.
     * 
     * @return List<RentalDTOPicture>
     */
    List<RentalDTOPicture> getAllRentals();

    /**
     * Return a rental, given its id.
     * 
     * @param id
     * @return RentalDTO
     * @throws RentalNotFoundException if the rental was not found
     */
    RentalDTO findById(int id) throws RentalNotFoundException;

    /**
     * Saves the given rental.
     * 
     * @param rentalRegisterDTO
     * @throws RentalNotFoundException
     * @throws UserNotFoundException
     * @throws IOException
     */
    void saveRental(RentalRegisterDTO rentalRegisterDTO)
            throws RentalNotFoundException, UserNotFoundException, IOException;

    /**
     * Updates the given rental.
     * 
     * @param rentalUpdateDTO
     * @throws RentalNotFoundException if the rental was not found
     */
    void updateRental(RentalUpdateDTO rentalUpdateDTO) throws RentalNotFoundException;
}
