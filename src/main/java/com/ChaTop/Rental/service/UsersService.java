package com.ChaTop.Rental.service;

import com.ChaTop.Rental.DTO.UserLoginDTO;
import com.ChaTop.Rental.DTO.UserRegisterDTO;
import com.ChaTop.Rental.entity.User;
import com.ChaTop.Rental.exception.BadCredentialsCustomException;
import com.ChaTop.Rental.exception.UserAlreadyExistsException;

public interface UsersService {
    
    public User saveUser(UserRegisterDTO userDTOToSave) throws UserAlreadyExistsException;

    public User authorizedUser(UserLoginDTO userLoginDTO) throws BadCredentialsCustomException;

}
