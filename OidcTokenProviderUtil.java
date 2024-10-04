package com.anita.multipleauthapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final AppProperties appProperties;
    private Algorithm ALGORITHM;

    // Initialize Algorithm
    public void init() {
        this.ALGORITHM = Algorithm.HMAC256(appProperties.getAuth().getTokenSecret());
    }

    // Method to decode JWT token
    public DecodedJWT decodeToken(String token) {
        init();
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        return verifier.verify(token);
    }

    // Method to check if token is expired
    public boolean isTokenExpired(DecodedJWT decodedJWT) {
        Date expiration = decodedJWT.getExpiresAt();
        return expiration.before(new Date());
    }

    // Extract email from JWT claims
    public String getEmailFromToken(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getClaim("email").asString();
    }

    // Validate the token for expiry
    public boolean validateToken(String token) {
        try {
            DecodedJWT decodedJWT = decodeToken(token);
            return !isTokenExpired(decodedJWT);
        } catch (Exception e) {
            log.error("Invalid or expired JWT.");
            return false;
        }
    }
}
