package com.ChaTop.Rental.service;

import com.ChaTop.Rental.DTO.UserDTO;
import com.ChaTop.Rental.DTO.UserLoginDTO;
import com.ChaTop.Rental.DTO.UserRegisterDTO;
import com.ChaTop.Rental.exception.BadCredentialsCustomException;
import com.ChaTop.Rental.exception.UserAlreadyExistsException;
import com.ChaTop.Rental.exception.UserNotFoundException;

public interface UsersService {

    /**
     * Saves user.
     * 
     * @param userDTOToSave
     * @return String containing the token if the user was correctly saved
     * @throws UserAlreadyExistsException if there is already a user with the same
     *                                    email adress
     */
    String saveUser(UserRegisterDTO userDTOToSave) throws UserAlreadyExistsException;

    /**
     * Checks if the credentials are corrects.
     * 
     * @param userLoginDTO
     * @return String containing the token if the credentials are correct
     * @throws BadCredentialsCustomException if the credentials are not correct
     */
    String validateCredentials(UserLoginDTO userLoginDTO) throws BadCredentialsCustomException;

    /**
     * Returns the corresponding user, given his email.
     * 
     * @param email
     * @return UserDTO
     * @throws UserNotFoundException if the user was not found
     */
    UserDTO findByEmail(String email) throws UserNotFoundException;

    /**
     * Returns the corresponding user, given his id.
     * 
     * @param id
     * @return UserDTO
     * @throws UserNotFoundException if the user was not found
     */
    UserDTO findById(int id) throws UserNotFoundException;
}
