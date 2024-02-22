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
import com.ChaTop.Rental.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    private JWTServiceImpl jwtService;

    private static final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
            JWTServiceImpl jwtService) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Saves user in database.
     * 
     * @param userDTOToSave
     * @return String containing the token if the user was correctly saved
     * @throws UserAlreadyExistsException if there is already a user with the same
     *                                    email adresse in database
     */
    @Override
    public String saveUser(UserRegisterDTO userDTOToSave) throws UserAlreadyExistsException {

        log.info("Trying to save user : {}", userDTOToSave);

        Optional<User> optionalUser = usersRepository.findByEmail(userDTOToSave.getEmail());

        if (optionalUser.isPresent()) {
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

    /**
     * Checks in database if the credentials are corrects.
     * 
     * @param userLoginDTO
     * @return String containing the token if the credentials are correct
     * @throws BadCredentialsCustomException if the credentials are not correct
     */
    @Override
    public String validateCredentials(UserLoginDTO userLoginDTO) throws BadCredentialsCustomException {

        log.info("Trying to validate credential of user with email {}", userLoginDTO.getEmail());

        Optional<User> optionalUser = usersRepository.findByEmail(userLoginDTO.getEmail());

        if (!optionalUser.isPresent()
                || !this.bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), optionalUser.get().getPassword())) {
            log.error("Invalid email or password");
            throw new BadCredentialsCustomException("error");
        }

        log.info("User with email {} successfully identified", userLoginDTO.getEmail());

        return jwtService.generateToken(userLoginDTO);
    }

    /**
     * Returns the corresponding user from database, given his email.
     * 
     * @param email
     * @return UserDTO
     * @throws UserNotFoundException if the user was not found in database
     */
    @Override
    public UserDTO findByEmail(String email) throws UserNotFoundException {

        log.info("Searching user with email {}", email);

        Optional<User> optionalUser = usersRepository.findByEmail(email);

        if (!optionalUser.isPresent()) {
            log.error("User with email {} not found", email);
            throw new UserNotFoundException("User with email " + email + " not found");
        }

        User user = optionalUser.get();

        log.info("User with email {} found", email);

        ModelMapper mapper = new ModelMapper();
        UserDTO userDTO = mapper.map(user, UserDTO.class);

        return userDTO;
    }

    /**
     * Returns the corresponding user from database, given his id.
     * 
     * @param id
     * @return UserDTO
     * @throws UserNotFoundException if the user was not found in database
     */
    @Override
    public UserDTO findById(int id) throws UserNotFoundException {

        log.info("Searching user with id {}", id);

        Optional<User> optionalUser = usersRepository.findById(id);

        if (!optionalUser.isPresent()) {
            log.error("User with id {} not found", id);
            throw new UserNotFoundException("User with id " + id + " not found");
        }

        User user = optionalUser.get();

        log.info("User with id {} found", id);

        ModelMapper mapper = new ModelMapper();
        UserDTO userDTO = mapper.map(user, UserDTO.class);

        return userDTO;
    }

}
