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
import com.ChaTop.Rental.exception.UserAlreadyExistsException;
import com.ChaTop.Rental.exception.UserNotFoundException;
import com.ChaTop.Rental.repository.UsersRepository;
import com.ChaTop.Rental.service.JWTService;
import com.ChaTop.Rental.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    private JWTService jwtService;

    private static final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JWTService jwtService) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService =jwtService;
    }

    @Override
    public String saveUser(UserRegisterDTO userDTOToSave) throws UserAlreadyExistsException {
        
        log.info("Trying to save user : " + userDTOToSave.toString());

        Optional<User> optionalUser = usersRepository.findByEmail(userDTOToSave.getEmail());

        if(optionalUser.isPresent()) {
            log.error("User with email {} already exists", userDTOToSave.getEmail());
            throw new UserAlreadyExistsException("User with email " + userDTOToSave.getEmail() + " already exists "); 
        }
        
        userDTOToSave.setPassword(this.bCryptPasswordEncoder.encode(userDTOToSave.getPassword()));

        ModelMapper mapper = new ModelMapper();
        User userToSave = mapper.map(userDTOToSave, User.class);
        userToSave.setCreated_at(LocalDate.now());
        userToSave.setUpdated_at(LocalDate.now());

        this.usersRepository.save(userToSave);
        
        log.info("User with email {} successfully saved", userDTOToSave.getEmail());
        
        return jwtService.generateToken(new UserLoginDTO(userDTOToSave.getEmail(), userDTOToSave.getPassword()));
    }

    @Override
    public String validateCredentials(UserLoginDTO userLoginDTO) throws BadCredentialsCustomException {

        Optional<User> optionalUser = usersRepository.findByEmail(userLoginDTO.getEmail());
 
        if(!optionalUser.isPresent() || !this.bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), optionalUser.get().getPassword())) {
            log.error("Invalid email or password");
            throw new BadCredentialsCustomException("error");
        }

        return jwtService.generateToken(userLoginDTO);
    }

    // TODO : Type renvoi : authentication 
    @Override
    public UserDTO findByEmail(String email) throws UserNotFoundException {
        
        Optional<User> optionalUser = usersRepository.findByEmail(email);
            
        if(!optionalUser.isPresent()) {
            log.error("User not found");
            throw new UserNotFoundException("User not found");
        }

        User user = optionalUser.get();

        ModelMapper mapper = new ModelMapper();
        UserDTO userDTO = mapper.map(user, UserDTO.class);

        return userDTO;
    }

    @Override
    public UserDTO findById(int id) throws UserNotFoundException {

        Optional<User> optionalUser = usersRepository.findById(id);

        if(!optionalUser.isPresent()) {
            log.error("User not found");
            throw new UserNotFoundException("User not found");
        }

        User user = optionalUser.get();

        ModelMapper mapper = new ModelMapper();
        UserDTO userDTO = mapper.map(user, UserDTO.class);

        return userDTO;   
    }

}
