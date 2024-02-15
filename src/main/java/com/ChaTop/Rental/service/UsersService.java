package com.ChaTop.Rental.service;

import com.ChaTop.Rental.DTO.UserLoginDTO;
import com.ChaTop.Rental.DTO.UserRegisterDTO;
import com.ChaTop.Rental.entity.User;
import com.ChaTop.Rental.exception.BadCredentialsCustomException;
import com.ChaTop.Rental.exception.UserAlreadyExistsException;
import com.ChaTop.Rental.exception.UserNotFoundException;

public interface UsersService {
    
    public User saveUser(UserRegisterDTO userDTOToSave) throws UserAlreadyExistsException;

    public void validateCredentials(UserLoginDTO userLoginDTO) throws BadCredentialsCustomException;

    public User findByEmail(String email) throws UserNotFoundException ;

    public User findById(int id) throws UserNotFoundException ;
}
