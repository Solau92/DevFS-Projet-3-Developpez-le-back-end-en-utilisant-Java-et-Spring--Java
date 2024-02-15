package com.ChaTop.Rental.service.Implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ChaTop.Rental.DTO.RentalDTO;
import com.ChaTop.Rental.DTO.RentalRegisterDTO;
import com.ChaTop.Rental.entity.Rental;
import com.ChaTop.Rental.repository.RentalsRepository;
import com.ChaTop.Rental.service.RentalsService;

@Service
public class RentalsServiceImpl implements RentalsService {

    private RentalsRepository rentalsRepository;

    private static final Logger log = LoggerFactory.getLogger(RentalsServiceImpl.class);

    public RentalsServiceImpl(RentalsRepository rentalsRepository) {
        this.rentalsRepository = rentalsRepository;
    }

    @Override
    public List<Rental> getAllRentals() {
        log.info("Find all rentals");
        return rentalsRepository.findAll();        
    }

    @Override
    public RentalDTO findById(int id) {

        Optional<Rental> optionalRental = rentalsRepository.findById(id);

        // Gérer erreur ? 
        Rental rental = optionalRental.get();
        String[] picture = {rental.getPicture()};
        RentalDTO rentalDTO = new RentalDTO(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), picture, rental.getDescription(), rental.getOwner_id(), rental.getCreated_at(), rental.getUpdated_at());

        return rentalDTO;
    }

    @Override
    public Rental saveRental(RentalRegisterDTO rentalDTOToSave) {

        rentalDTOToSave.setCreated_at(LocalDate.now());
        // Rajouter le owner_id
        
        // Gestion image 
        String imageURL = /*TODO*/"";
        Rental rentalToSave = new Rental(rentalDTOToSave.getName(), rentalDTOToSave.getSurface(), rentalDTOToSave.getPrice(), imageURL, rentalDTOToSave.getDescription(), rentalDTOToSave.getOwner_id(), rentalDTOToSave.getCreated_at());
    
        return rentalsRepository.save(rentalToSave);
    }
    
}
