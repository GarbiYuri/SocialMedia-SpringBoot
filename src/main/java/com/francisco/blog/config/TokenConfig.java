package com.francisco.blog.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.francisco.blog.entitys.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenConfig {

    @Value("{api.security.token.secret}")
    private String secret;

    public String generateToken(User usuario){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim("userId", usuario.getId())
                .withClaim("roles", usuario.getUserRole().stream().map(Enum::name).toList())
                .withSubject(usuario.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(1800))
                .withIssuedAt(Instant.now())
                .sign(algorithm);

    }

    public Optional<JWTUserData> validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build().verify(token);
            return Optional.of(JWTUserData.builder()
                    .userId(decodedJWT.getClaim("userId").asLong())
                    .email(decodedJWT.getSubject())
                            .roles(decodedJWT.getClaim("roles").asList(String.class))
                    .build());
        }catch (JWTVerificationException e){
            return Optional.empty();
        }
    }

}
