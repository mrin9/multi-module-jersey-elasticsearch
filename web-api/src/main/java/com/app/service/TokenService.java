package com.app.service;

import com.app.model.user.Role;
import com.app.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TokenService {
    
    private static final long VALIDITY_TIME_MS =  2 * 60 * 60 * 1000; // 2 hours  validity
    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String secret="mrin";

    //Get User Info from the Token
    public static User getUserFromToken(String token){
        Claims claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();

        User user = new User();
        user.setUserId( (String)claims.get("userId"));
        user.setRole(Role.valueOf((String)claims.get("role")));
        return user;
    }

    public static String createTokenForUser(User user) {
      return Jwts.builder()
        .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
        .setSubject(user.getUserName())
        .claim("userId", user.getUserId())
        .claim("role", user.getRole().toString().toUpperCase())
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
    }
    
    public static boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token.trim());
            return true;
        }
        catch (ExpiredJwtException e){
            log.error("Token Expired");
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    
}
