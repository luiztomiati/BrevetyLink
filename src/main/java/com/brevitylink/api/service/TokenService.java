package com.brevitylink.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.brevitylink.api.model.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${JWT_SECRET}")
    private String secret;

    public String generateToken(Users user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Instant now = Instant.now();
            Instant expiration = now.plus(30, java.time.temporal.ChronoUnit.MINUTES);

            return JWT.create()
                    .withIssuer("Brevity Link")
                    .withSubject(user.getUsername())
                    .withIssuedAt(now)
                    .withExpiresAt(expiration)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao criar token", exception);
        }
    }

    private Instant expiraction(int minutes) {
        return LocalDateTime.now()
                .plusMinutes(minutes)
                .toInstant(ZoneOffset.UTC);
    }

    public String checkToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Brevity Link")
                    .acceptLeeway(10)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception){
            return null;
        }
    }

    public String generateRefreshToken(Users user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Brevity Link")
                    .withSubject(user.getId().toString())
                    .withExpiresAt(expiractionDays(7))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new ExceptionInInitializerError(exception);
        }
    }
    private Instant expiractionDays(int days) {
        return LocalDateTime.now().plusDays(days).toInstant(ZoneOffset.UTC);
    }
}
