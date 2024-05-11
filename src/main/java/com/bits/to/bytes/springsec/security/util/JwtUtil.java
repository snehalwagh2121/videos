package com.bits.to.bytes.springsec.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    public String generateJwtToken(UserDetails userDetails){
        Map<String, Object> claims= new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 5*60 *1000))
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateJwtToken(String token, UserDetails userDetails){
        String username= userDetails.getUsername();
        Claims claims= Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String userNameFromToken= claims.getSubject();
        boolean isTokenExpiration= claims.getExpiration().before(new Date());
        return (username.equals(userNameFromToken) && !isTokenExpiration);
    }


    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }


}
