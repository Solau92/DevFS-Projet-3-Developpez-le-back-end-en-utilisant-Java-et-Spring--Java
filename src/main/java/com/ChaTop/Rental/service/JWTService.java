package com.ChaTop.Rental.service;

import com.ChaTop.Rental.DTO.UserLoginDTO;

public interface JWTService {

    /**
     * Generates token if the given user is properly identified.
     * 
     * @param userLoginDto
     * @return String corresponding to the token
     */
    String generateToken(UserLoginDTO userLoginDto);

}
