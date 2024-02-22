package com.ChaTop.Rental.service.Implementation;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ChaTop.Rental.DTO.RentalDTOPicture;
import com.ChaTop.Rental.DTO.RentalDTOTabPicture;
import com.ChaTop.Rental.DTO.RentalRegisterDTO;
import com.ChaTop.Rental.DTO.RentalUpdateDTO;
import com.ChaTop.Rental.entity.Rental;
import com.ChaTop.Rental.exception.RentalNotFoundException;
import com.ChaTop.Rental.exception.UserNotFoundException;
import com.ChaTop.Rental.repository.RentalsRepository;
import com.ChaTop.Rental.service.RentalsService;
import com.ChaTop.Rental.service.UsersService;

import org.springframework.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RentalsServiceImpl implements RentalsService {

    private RentalsRepository rentalsRepository;

    private UsersService usersService;

    private static final Logger log = LoggerFactory.getLogger(RentalsServiceImpl.class);

    @Value("${picture-upload-directory}")
    private String uploadDir;

    @Value("${picture-upload-directory-path}")
    private String uploadDirPath;

    @Value("${root-url}")
    private String rootUrl;

    public RentalsServiceImpl(RentalsRepository rentalsRepository, UsersService usersService) {
        this.rentalsRepository = rentalsRepository;
        this.usersService = usersService;
    }

    /**
     * Returns a list of all rentals from database.
     * 
     * @return List<RentalDTOPicture>
     */
    @Override
    public List<RentalDTOPicture> getAllRentals() {

        log.info("Find all rentals");

        List<Rental> rentals = rentalsRepository.findAll();
        List<RentalDTOPicture> rentalsDTO = new ArrayList<RentalDTOPicture>();

        ModelMapper mapper = new ModelMapper();

        for (Rental r : rentals) {
            RentalDTOPicture rDTO = mapper.map(r, RentalDTOPicture.class);
            rentalsDTO.add(rDTO);
        }
        return rentalsDTO;
    }

    /**
     * Return a rental from database, given its id.
     * 
     * @param id
     * @return RentalDTO
     * @throws RentalNotFoundException if the rental was not found in database
     */
    @Override
    public RentalDTOTabPicture findById(int id) throws RentalNotFoundException {

        log.info("Searching rental with id {}", id);

        Optional<Rental> optionalRental = rentalsRepository.findById(id);

        if (!optionalRental.isPresent()) {
            log.error("Rental with id {} not found", id);
            throw new RentalNotFoundException("Rental with id " + id + "not found");
        }

        Rental rental = optionalRental.get();
        String[] picture = { rental.getPicture() };

        log.info("Rental with id {} found", id);

        ModelMapper mapper = new ModelMapper();
        RentalDTOTabPicture rentalDTO = mapper.map(rental, RentalDTOTabPicture.class);
        rentalDTO.setPicture(picture);

        return rentalDTO;
    }

    /**
     * Saves in database the given rental.
     * 
     * @param rentalRegisterDTO
     * @throws RentalNotFoundException
     * @throws UserNotFoundException
     * @throws IOException
     */
    @Override
    public void saveRental(RentalRegisterDTO rentalDTOToSave) throws UserNotFoundException, IOException {

        log.info("Trying to save rental : {}", rentalDTOToSave);

        Rental rentalToSave = new Rental();

        // Set and transformer type of price and surface
        rentalToSave.setPrice(Integer.valueOf(rentalDTOToSave.getPrice()));
        rentalToSave.setSurface(Integer.valueOf(rentalDTOToSave.getSurface()));
        // Set dates
        rentalToSave.setCreated_at(LocalDate.now());
        rentalToSave.setUpdated_at(LocalDate.now());
        // Set OwnerID
        int ownerId = usersService.findByEmail(rentalDTOToSave.getOwnerEmail()).getId();
        rentalToSave.setOwner_id(ownerId);
        // Set other fields
        rentalToSave.setName(rentalDTOToSave.getName());
        rentalToSave.setDescription(rentalDTOToSave.getDescription());
        // Upload file and set returned URL
        rentalToSave.setPicture(this.uploadFileAndReturnURL(rentalDTOToSave.getPicture(), ownerId));

        rentalsRepository.save(rentalToSave);
    }

    /**
     * Updates the given rental.
     * 
     * @param rentalUpdateDTO
     * @throws RentalNotFoundException if the rental was not found in database
     */
    @Override
    public void updateRental(RentalUpdateDTO rentalUpdateDTO) throws RentalNotFoundException {

        log.info("Trying to update rental : {}", rentalUpdateDTO);

        RentalDTOTabPicture rentalDTO = this.findById(rentalUpdateDTO.getId());

        Rental rentalToUpdate = new Rental();

        // Set and transformer type of price and surface
        rentalToUpdate.setSurface(Integer.valueOf(rentalUpdateDTO.getSurface()));
        rentalToUpdate.setPrice(Integer.valueOf(rentalUpdateDTO.getPrice()));
        // Set dates
        rentalToUpdate.setCreated_at(rentalDTO.getCreated_at());
        rentalToUpdate.setUpdated_at(LocalDate.now());
        // Set other fields
        rentalToUpdate.setId(rentalUpdateDTO.getId());
        rentalToUpdate.setName(rentalUpdateDTO.getName());
        rentalToUpdate.setPicture(rentalDTO.getPicture()[0]);
        rentalToUpdate.setDescription(rentalUpdateDTO.getDescription());
        rentalToUpdate.setOwner_id(rentalDTO.getOwner_id());

        rentalsRepository.save(rentalToUpdate);
    }

    /**
     * Updload picture file in a directory set in applications.properties file, and
     * returns the URL of the image that will be set in database
     * 
     * @param pictureFile
     * @param ownerId
     * @return String the URL of the image that will be set in database
     * @throws IOException
     */
    private String uploadFileAndReturnURL(MultipartFile pictureFile, int ownerId) throws IOException {

        log.info("Trying to updload image named {} (user id {})", pictureFile.getOriginalFilename(), ownerId);
        String fileName = StringUtils.cleanPath(pictureFile.getOriginalFilename());

        Path uploadPath = Paths.get(uploadDirPath + uploadDir);

        if (!Files.exists(uploadPath)) {
            log.info("Image directory created");
            Files.createDirectories(uploadPath);
        }

        fileName = UUID.randomUUID() + fileName;

        String URL = rootUrl + uploadDir + "/" + fileName;

        try (InputStream inputStream = pictureFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            log.error("Error saving image file : {}", fileName);
            throw new IOException("Could not save image file: " + fileName, ioe);
        }

        log.info("Image correctly uploaded");

        return URL;

    }

}
