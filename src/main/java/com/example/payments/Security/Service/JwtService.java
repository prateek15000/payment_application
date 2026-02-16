package com.example.payments.Security.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

public class JwtService {

    private final String secret="kVFRzJUpC4e6mAJvUSWVJ6x3jiHQEwUt1NLDREnUkqnucZzXfN/yYRnypj32PF94cIJ8fLhRmF2iaOIHsjv3Bg==";

    public String extractEmail(String token){
        return extractClaims(token , Claims::getSubject);
    }

    public <T> T extractClaims(String token , Function<Claims , T> claimsResolver){
        final Claims claims = getTokenClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims getTokenClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignInKey()) // create Later
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(
            Map<String , Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60*24)) // 1 day
                .signWith(getSignInKey())
                .compact();
    }

    public String returnGeneratedToken(UserDetails userDetails){
        return generateToken(new HashMap<>() , userDetails);
    }

    private Boolean isTokenValid(String token , UserDetails userDetails){
        final String tokemUserEmail = extractEmail(token);
        return ( tokemUserEmail.equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }

    private Boolean isTokenExpired(String token){
        return expirationDate(token).before(new Date());
    }

    private Date expirationDate(String token){
        return extractClaims(token , Claims::getExpiration);
    }
}
