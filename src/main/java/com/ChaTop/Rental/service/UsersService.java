package com.ChaTop.Rental.service;

import com.ChaTop.Rental.DTO.UserDTO;
import com.ChaTop.Rental.DTO.UserLoginDTO;
import com.ChaTop.Rental.DTO.UserRegisterDTO;
import com.ChaTop.Rental.exception.BadCredentialsCustomException;
import com.ChaTop.Rental.exception.ErrorSavingUserException;
import com.ChaTop.Rental.exception.UserAlreadyExistsException;
import com.ChaTop.Rental.exception.UserNotFoundException;

public interface UsersService {
    
    public void saveUser(UserRegisterDTO userDTOToSave) throws UserAlreadyExistsException, ErrorSavingUserException;

    public void validateCredentials(UserLoginDTO userLoginDTO) throws BadCredentialsCustomException;

    public UserDTO findByEmail(String email) throws UserNotFoundException ;

    public UserDTO findById(int id) throws UserNotFoundException ;
}
