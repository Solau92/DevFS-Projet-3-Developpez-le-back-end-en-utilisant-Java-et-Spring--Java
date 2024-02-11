package com.ChaTop.Rental.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ChaTop.Rental.entity.User;
import com.ChaTop.Rental.repository.UsersRepository;

@Service
public class UsersService {

    private UsersRepository usersRepository;

    private static final Logger log = LoggerFactory.getLogger(UsersService.class);

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User saveUser(User user) throws Exception {
        // Gérer erreur si mail existe déjà 
        // --> créer méthode findByEmail() 
        log.info("Trying to save user : " + user.toString());

        Optional<User> optionalUser = usersRepository.findByEmail(user.getEmail());

        if(optionalUser.isPresent()) {
            log.error("User with email {} already exists", user.getEmail());
            throw new Exception("User with email " + user.getEmail() + " already exists "); 
    
        }
        log.info("User with email {} successfully saved", user.getEmail());
        return this.usersRepository.save(user);
    }

}
