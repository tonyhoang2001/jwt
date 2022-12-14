package com.example.springauthen.jwt;

import com.example.springauthen.user.api.Role;
import com.example.springauthen.user.api.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;

@Component
@Slf4j
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 100; // 24h

    @Value("${app.jwt.secret}")
    private String secretKey;

    public String generateAccessToken(User user) {

        Iterator<Role> iterator = user.getRoles().iterator();
        String roles = "";
        while (iterator.hasNext()) {
            roles += iterator.next().getName() + ",";
        }

        return Jwts
                .builder()
                .setSubject(user.getId() + ", " + user.getUsername())
                .claim("role", roles)
                .setIssuer("Tony")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT expired", e);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Token is null, empty or has only whitespace", e);
        } catch (MalformedJwtException e) {
            LOGGER.error("JWT is invalid", e);
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT is not supported", e);
        } catch (SignatureException e) {
            LOGGER.error("Signature validation failed", e);
        }
        return false;
    }

    public String getSubject(String token) {
        String subject = parseClaims(token).getSubject();
        return subject;
    }

    public Claims parseClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

}
