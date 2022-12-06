package com.example.springauthen.jwt;

import com.example.springauthen.user.api.Role;
import com.example.springauthen.user.api.User;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (!hasAuthorizationHeader(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = getAccessToken(request);

        if (!jwtTokenUtil.validateAccessToken(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContext(accessToken, request);
        System.out.println("Access token: " + accessToken);
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationContext(String accessToken, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(accessToken);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String accessToken) {
        User userDetails = new User();
        Claims claims = jwtTokenUtil.parseClaims(accessToken);

        String claimRoles = claims.get("role").toString();
        String[] roles = claimRoles.substring(0, claimRoles.length() - 1).split(",");
        for (String roleName : roles) {
            userDetails.getRoles().add(new Role(roleName));
        }

        String subject = (String) claims.get(Claims.SUBJECT);
        String[] subjectArray = subject.split(", ");

        userDetails.setId(Integer.parseInt(subjectArray[0]));
        userDetails.setUsername(subjectArray[1]);

        return userDetails;
    }

    private boolean hasAuthorizationHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        System.out.println("Authorization header: " + header);

        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }

        return true;
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }

}
