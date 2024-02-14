package com.ChaTop.Rental.service.Implementation;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    // private final ModelMapper modelMapper;

    public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder /*, ModelMapper modelMapper */) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        // this.modelMapper = modelMapper;
    }

    @Override
    public User saveUser(UserRegisterDTO userDTOToSave) throws UserAlreadyExistsException {
        
        log.info("Trying to save user : " + userDTOToSave.toString());

        Optional<User> optionalUser = usersRepository.findByEmail(userDTOToSave.getEmail());

        if(optionalUser.isPresent()) {
            log.error("User with email {} already exists", userDTOToSave.getEmail());
            throw new UserAlreadyExistsException("User with email " + userDTOToSave.getEmail() + " already exists "); 
        }
        log.info("User with email {} successfully saved", userDTOToSave.getEmail());

        userDTOToSave.setCreated_at(LocalDate.now());
        userDTOToSave.setPassword(this.bCryptPasswordEncoder.encode(userDTOToSave.getPassword()));

        // Faire mapping : ModelMapper ?
        // User userToSave = new User();
        // modelMapper.map(userToSave, usersRepository);
        // log.info(userToSave.toString());
        User userToSave = new User(userDTOToSave.getEmail(), userDTOToSave.getName(), userDTOToSave.getPassword(),userDTOToSave.getCreated_at());

        return this.usersRepository.save(userToSave);
    }

    @Override
    public void validateCredentials(UserLoginDTO userLoginDTO) throws BadCredentialsCustomException {

        // Vérifier si on a un utilisateur en bdd avec cet email et ce mdp 
        //Optional<User> optionalUser = this.findUserByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        
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
    public User findByEmail(String email) throws UserNotFoundException {
        
        Optional<User> optionalUser = usersRepository.findByEmail(email);
            
        if(!optionalUser.isPresent()) {
            log.error("User not found");
            // TODO : Quoi faire ?
            throw new UserNotFoundException("User not found");
        }

        return optionalUser.get();
    }

    @Override
    public User findById(int id) throws UserNotFoundException {

        Optional<User> optionalUser = usersRepository.findById(id);

        if(!optionalUser.isPresent()) {
            log.error("User not found");
            // TODO : Quoi faire ?
            throw new UserNotFoundException("User not found");
        }

        return optionalUser.get();
         
    }

}
