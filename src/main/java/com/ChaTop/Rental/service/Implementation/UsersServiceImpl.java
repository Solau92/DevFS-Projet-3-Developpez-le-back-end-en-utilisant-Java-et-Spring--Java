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
import com.ChaTop.Rental.repository.UsersRepository;
import com.ChaTop.Rental.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    private static final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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
        User userToSave = new User(userDTOToSave.getEmail(), userDTOToSave.getName(), userDTOToSave.getPassword(),userDTOToSave.getCreated_at());

        return this.usersRepository.save(userToSave);
    }

    public User authorizedUser(UserLoginDTO userLoginDTO) throws BadCredentialsCustomException {

        // Vérifier si on a un utilisateur en bdd avec cet email et ce mdp 
        Optional<User> optionalUser = this.findUserByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        
        if(!optionalUser.isPresent()) {
            log.error("Invalid email or password");
            throw new BadCredentialsCustomException("error");

        } else {
            // Mapper en DTO plutôt ?
             return optionalUser.get(); 
        }
    }

    private Optional<User> findUserByEmailAndPassword(String email, String password) {

        // Régler problème cryptage mdp 
        return usersRepository.findByEmailAndPassword(email, password);
    }



}
