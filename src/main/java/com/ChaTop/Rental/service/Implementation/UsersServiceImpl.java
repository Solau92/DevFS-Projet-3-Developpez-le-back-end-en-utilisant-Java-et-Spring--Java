package com.ChaTop.Rental.service.Implementation;

import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ChaTop.Rental.DTO.UserDTO;
import com.ChaTop.Rental.DTO.UserLoginDTO;
import com.ChaTop.Rental.DTO.UserRegisterDTO;
import com.ChaTop.Rental.entity.User;
import com.ChaTop.Rental.exception.BadCredentialsCustomException;
import com.ChaTop.Rental.exception.ErrorSavingUserException;
import com.ChaTop.Rental.exception.UserAlreadyExistsException;
import com.ChaTop.Rental.exception.UserNotFoundException;
import com.ChaTop.Rental.repository.UsersRepository;
import com.ChaTop.Rental.service.UsersService;

import jakarta.validation.ConstraintViolationException;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    private static final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder /*, ModelMapper modelMapper */) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // TODO question : Return void ? renvoyer token plutôt 
    @Override
    public void saveUser(UserRegisterDTO userDTOToSave) throws UserAlreadyExistsException, ErrorSavingUserException {
        
        log.info("Trying to save user : " + userDTOToSave.toString());

        Optional<User> optionalUser = usersRepository.findByEmail(userDTOToSave.getEmail());

        if(optionalUser.isPresent()) {
            log.error("User with email {} already exists", userDTOToSave.getEmail());
            throw new UserAlreadyExistsException("User with email " + userDTOToSave.getEmail() + " already exists "); 
        }
        
        userDTOToSave.setPassword(this.bCryptPasswordEncoder.encode(userDTOToSave.getPassword()));

        // User userToSave = new User(userDTOToSave.getEmail(), userDTOToSave.getName(), userDTOToSave.getPassword(),LocalDate.now());

        // TODO done : Mapper --> créer @Bean 
        ModelMapper mapper = new ModelMapper();
        User userToSave = mapper.map(userDTOToSave, User.class);
        userToSave.setCreated_at(LocalDate.now());

        // log.info("DTO : {}", userDTOToSave);
        // log.info("User : {}", userToSave);

        // TODO : contrainte validité à voir avant, ici sert à rien d'aller en BDD
        try {
            this.usersRepository.save(userToSave);
        } catch (ConstraintViolationException Exc) {
            log.error("Constraint violation exception");
            throw new ErrorSavingUserException("Error saving user");
        }
        
        log.info("User with email {} successfully saved", userDTOToSave.getEmail());
        
    }

    // TODO : A la place du void : renvoyer token directement 
    @Override
    public void validateCredentials(UserLoginDTO userLoginDTO) throws BadCredentialsCustomException {

        Optional<User> optionalUser = usersRepository.findByEmail(userLoginDTO.getEmail());

        // TODO : Factoriser les 2 if
 
        if(!optionalUser.isPresent()) {
            log.error("Invalid email");
            throw new BadCredentialsCustomException("error");
        }
        
        boolean correctPassword = this.bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), optionalUser.get().getPassword());

        if (!correctPassword) {
            log.error("Invalid password");
            throw new BadCredentialsCustomException("error");
        }
    }

    // TODO : Type renvoi : authentication 
    @Override
    public UserDTO findByEmail(String email) throws UserNotFoundException {
        
        Optional<User> optionalUser = usersRepository.findByEmail(email);
            
        // TODO : pas besoin, si email c'est que l'utilisateur est trouvé
        if(!optionalUser.isPresent()) {
            log.error("User not found");
            // TODO question : Quoi faire ?
            throw new UserNotFoundException("User not found");
        }

        User user = optionalUser.get();

        // TODO : mapping 
        UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getCreated_at(), user.getUpdated_at() == null ? null : user.getUpdated_at());

        return userDTO;
    }

    @Override
    public UserDTO findById(int id) throws UserNotFoundException {

        Optional<User> optionalUser = usersRepository.findById(id);

        if(!optionalUser.isPresent()) {
            log.error("User not found");
            // TODO question : Quoi faire ?
            throw new UserNotFoundException("User not found");
        }

        User user = optionalUser.get();

        // TODO : mapping 
        UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getCreated_at(), user.getUpdated_at() == null ? null : user.getUpdated_at());

        return userDTO;   
    }

}
