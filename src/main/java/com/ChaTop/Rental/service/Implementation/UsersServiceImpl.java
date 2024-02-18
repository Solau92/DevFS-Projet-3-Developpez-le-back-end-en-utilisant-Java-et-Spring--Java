package com.ChaTop.Rental.service.Implementation;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ChaTop.Rental.DTO.UserDTO;
import com.ChaTop.Rental.DTO.UserLoginDTO;
import com.ChaTop.Rental.DTO.UserRegisterDTO;
import com.ChaTop.Rental.entity.User;
import com.ChaTop.Rental.exception.BadCredentialsCustomException;
import com.ChaTop.Rental.exception.UserAlreadyExistsException;
import com.ChaTop.Rental.exception.UserNotFoundException;
import com.ChaTop.Rental.repository.UsersRepository;
import com.ChaTop.Rental.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    private static final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder /*, ModelMapper modelMapper */) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // TODO question : Return void ?
    @Override
    public void saveUser(UserRegisterDTO userDTOToSave) throws UserAlreadyExistsException {
        
        log.info("Trying to save user : " + userDTOToSave.toString());

        Optional<User> optionalUser = usersRepository.findByEmail(userDTOToSave.getEmail());

        if(optionalUser.isPresent()) {
            log.error("User with email {} already exists", userDTOToSave.getEmail());
            throw new UserAlreadyExistsException("User with email " + userDTOToSave.getEmail() + " already exists "); 
        }
        log.info("User with email {} successfully saved", userDTOToSave.getEmail());

        userDTOToSave.setCreated_at(LocalDate.now());
        userDTOToSave.setPassword(this.bCryptPasswordEncoder.encode(userDTOToSave.getPassword()));

        User userToSave = new User(userDTOToSave.getEmail(), userDTOToSave.getName(), userDTOToSave.getPassword(),userDTOToSave.getCreated_at());

        this.usersRepository.save(userToSave);
    }

    @Override
    public void validateCredentials(UserLoginDTO userLoginDTO) throws BadCredentialsCustomException {

        Optional<User> optionalUser = usersRepository.findByEmail(userLoginDTO.getEmail());

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

    @Override
    public UserDTO findByEmail(String email) throws UserNotFoundException {
        
        Optional<User> optionalUser = usersRepository.findByEmail(email);
            
        if(!optionalUser.isPresent()) {
            log.error("User not found");
            // TODO question : Quoi faire ?
            throw new UserNotFoundException("User not found");
        }

        User user = optionalUser.get();

        // TODO : mapping 
        // TODO question : Que faire pour le mdp ?
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
        // TODO question : Que faire pour le mdp ?
        UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getCreated_at(), user.getUpdated_at() == null ? null : user.getUpdated_at());

        return userDTO;   
    }

}
