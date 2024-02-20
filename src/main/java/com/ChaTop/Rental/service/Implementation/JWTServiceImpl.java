package com.ChaTop.Rental.service.Implementation;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.ChaTop.Rental.DTO.UserLoginDTO;
import com.ChaTop.Rental.service.JWTService;

@Service
public class JWTServiceImpl implements JWTService {

    private JwtEncoder jwtEncoder;

    public JWTServiceImpl(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    /**
     * Generates token if the given user is properly identified.
     * 
     * @param userLoginDto
     * @return String corresponding to the token
     */
    public String generateToken(UserLoginDTO userLoginDto) {

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(userLoginDto.getEmail())
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters
                .from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
                
        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }
}
