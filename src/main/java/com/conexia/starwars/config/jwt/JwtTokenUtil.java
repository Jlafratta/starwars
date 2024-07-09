package com.conexia.starwars.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    // Retorna el username perteneciente al token
    public String getApiKeyFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Retorna la fecha de expiracion del token
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Obtiene la data del token mediante la secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Valida la expiracion del token
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Genera el token para el usuario
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return "Bearer " + doGenerateToken(claims, userDetails.getUsername());
    }

    // Crea el token
    //1. Define los attrs del token (apikey, fecha de seteo, fecha de expiracion)
    //2. Cierra el token con el algoritmo HS512 y la key.
    private String doGenerateToken(Map<String, Object> claims, String apiKey) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(apiKey)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // Valida el token del usuario segun igualdad y expiracion
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String apikey = getApiKeyFromToken(token);
        return (apikey.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
