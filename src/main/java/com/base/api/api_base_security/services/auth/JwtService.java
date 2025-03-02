package com.base.api.api_base_security.services.auth;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service 
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    String generateToken(UserDetails user,Map<String,Object> extraClaims){
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((EXPIRATION_IN_MINUTES*6*1000) + issuedAt.getTime());
        String jwt = Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
            .signWith(generatedKey(),SignatureAlgorithm.HS256)
            .compact();
        return jwt;
    }

    private Key generatedKey() {
        byte[] passwordDecodeString = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(passwordDecodeString);
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }
        
    private Claims extractAllClaims(String jwt) {
        return Jwts.parser()
            .setSigningKey(generatedKey()).build()
            .parseClaimsJws(jwt).getBody();
    }
    
}
