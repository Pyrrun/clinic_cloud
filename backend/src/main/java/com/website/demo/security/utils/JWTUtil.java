package com.website.demo.security.utils;



import com.website.demo.API.user.AppUser;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;


    public String generateToken(AppUser appUser) {
        Claims claims = Jwts.claims().setSubject(appUser.getEmail());
        claims.put("role", appUser.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims parseToken(String token) {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
    }

    public String getEmailFromToken(String token) {
        return parseToken(token).getSubject();
    }

}
