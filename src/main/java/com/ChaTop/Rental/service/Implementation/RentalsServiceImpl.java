package com.ChaTop.Rental.service.Implementation;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ChaTop.Rental.DTO.RentalDTOPicture;
import com.ChaTop.Rental.DTO.RentalDTOTabPicture;
import com.ChaTop.Rental.DTO.RentalRegisterDTO;
import com.ChaTop.Rental.DTO.RentalUpdateDTO;
import com.ChaTop.Rental.entity.Rental;
import com.ChaTop.Rental.exception.UserNotFoundException;
import com.ChaTop.Rental.repository.RentalsRepository;
import com.ChaTop.Rental.service.RentalsService;
import com.ChaTop.Rental.service.UsersService;

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

    @Override
    public List<RentalDTOPicture> getAllRentals() {
        log.info("Find all rentals");

        List<Rental> rentals = rentalsRepository.findAll();
        List<RentalDTOPicture> rentalsDTO = new ArrayList<RentalDTOPicture>();

        //TO DO : mapping 
        for(Rental r : rentals) {
            RentalDTOPicture rDTO = new RentalDTOPicture(r.getId(), r.getName(), r.getSurface(), r.getPrice(), r.getDescription(), r.getOwner_id(), r.getCreated_at(), r.getUpdated_at() == null ? null : r.getUpdated_at(), r.getPicture());
                        rentalsDTO.add(rDTO);
        }
        return rentalsDTO;        
    }

    @Override
    public RentalDTOTabPicture findById(int id) {

        Optional<Rental> optionalRental = rentalsRepository.findById(id);

        // TODO question : Gérer erreur ? 
        Rental rental = optionalRental.get();
        String[] picture = {rental.getPicture()};

        //TO DO : mapping 
        RentalDTOTabPicture rentalDTO = new RentalDTOTabPicture(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), rental.getDescription(), rental.getOwner_id(), rental.getCreated_at(), rental.getUpdated_at(), picture);

        return rentalDTO;
    }

    // TODO : quand on génère token, on met l'email, mais on peut metttre aussi l'id --> à voir (pour éviter recherche owner id), voir subject
    @Override
    public void saveRental(RentalRegisterDTO rentalDTOToSave) throws UserNotFoundException, IOException {

        Rental rentalToSave = new Rental();

        //TO DO : mapping 

        // Transformer champs String --> int 
        rentalToSave.setPrice(Integer.valueOf(rentalDTOToSave.getPrice()));
        rentalToSave.setSurface(Integer.valueOf(rentalDTOToSave.getSurface()));
        // Set date
        rentalToSave.setCreated_at(LocalDate.now());
        // Set OwnerID
        int ownerId = usersService.findByEmail(rentalDTOToSave.getOwnerEmail()).getId();
        rentalToSave.setOwner_id(ownerId);

        // Set autres champs
        rentalToSave.setName(rentalDTOToSave.getName());
        rentalToSave.setDescription(rentalDTOToSave.getDescription());

        // Gérer fichier, à uploader et retourner URL 
        rentalToSave.setPicture(this.uploadFileAndReturnURL(rentalDTOToSave.getPicture()));
    
        rentalsRepository.save(rentalToSave);
    }

    @Override
    public void updateRental(RentalUpdateDTO rentalUpdateDTO) {
        
        RentalDTOTabPicture rentalDTO = this.findById(rentalUpdateDTO.getId());

        //TO DO : mapping 
        Rental rentalToUpdate = new Rental();

        // Set tous les chames et transformer champs String --> int 
        rentalToUpdate.setId(rentalUpdateDTO.getId());
        rentalToUpdate.setName(rentalUpdateDTO.getName());
        rentalToUpdate.setSurface(Integer.valueOf(rentalUpdateDTO.getSurface()));
        rentalToUpdate.setPrice(Integer.valueOf(rentalUpdateDTO.getPrice()));
        rentalToUpdate.setPicture(rentalDTO.getPicture()[0]);
        rentalToUpdate.setDescription(rentalUpdateDTO.getDescription());
        rentalToUpdate.setOwner_id(rentalDTO.getOwner_id());
        rentalToUpdate.setCreated_at(rentalDTO.getCreated_at());
        rentalToUpdate.setUpdated_at(LocalDate.now());

        rentalsRepository.save(rentalToUpdate);
    }

    private String uploadFileAndReturnURL(MultipartFile pictureFile) throws IOException {

        String fileName = StringUtils.cleanPath(pictureFile.getOriginalFilename());
        
        Path uploadPath = Paths.get(uploadDirPath + uploadDir);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        //TODO question : changer nom image ? 

        String URL = rootUrl + uploadDir + "/" + fileName;

        try (InputStream inputStream = pictureFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }       

        return URL;

    }
    
    
}
