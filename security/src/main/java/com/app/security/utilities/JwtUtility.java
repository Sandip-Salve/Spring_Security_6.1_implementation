package com.app.security.utilities;

import com.app.security.config.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtUtility {

    @Autowired
    private Environment environment;

    public String generateToken(AuthUser authUser){
        Map<String,Object> info = new HashMap<>();
        info.put("username",authUser.getUsername());
        info.put("authorities",authUser.getAuthorities());
        Long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = Long.parseLong(environment.getProperty("jwt.token.expiry.millis"));
        String secret = environment.getProperty("jwt.token.secret");
        SecretKey key = Keys.hmacShaKeyFor(
                secret.getBytes()
        );
        return Jwts.builder()
                .setSubject(authUser.getUsername())
                .setClaims(info)
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTimeMillis+expirationTimeMillis))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims decodeJwtToken(String token){
        String secret = environment.getProperty("jwt.token.secret");
        SecretKey key = Keys.hmacShaKeyFor(
                secret.getBytes()
        );
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateJwtToken(String token,AuthUser authUser){
        Claims claims = decodeJwtToken(token);
        if(!(claims.get("username",String.class).equals(authUser.getUsername()))){
            return false;
        }
//        if(!(Objects.equals(claims.get("authorities"),authUser.getAuthorities()))){
//            return false;
//        }
        if(claims.getExpiration().before(new Date())){
            return false;
        }
        return true;
    }

    public String getUserNameFromToken(String token){
        Claims claims = decodeJwtToken(token);
        return claims.get("username",String.class);
    }
}
