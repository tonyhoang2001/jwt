package com.example.springauthen.jwt;

import com.example.springauthen.user.api.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 100; // 24h

    @Value("${app.jwt.secret}")
    private String secretKey;

    public String generateAccessToken(User user) {
        return Jwts
                .builder()
                .setSubject(user.getId() + ", " + user.getUsername())
                .setIssuer("Tony")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT expired", e);
        } catch (IllegalArgumentException e){
            LOGGER.error("Token is null, empty or has only whitespace",e);
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT expired", e);
        } catch (IllegalArgumentException e){
            LOGGER.error("Token is null, empty or has only whitespace",e);
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT expired", e);
        }
        return false;
    }

}
