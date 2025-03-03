package com.base.api.api_base_security.config.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.base.api.api_base_security.exception.ObjectNotFoundException;
import com.base.api.api_base_security.persistence.entity.User;
import com.base.api.api_base_security.services.UserService;
import com.base.api.api_base_security.services.auth.JwtService;

import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserService userService;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Get Http Authorization 
        String authorizationHeader = request.getHeader("Authorization"); // Bearer jwt
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            log.warn("No Authorization header found or does not start with Bearer.");
            filterChain.doFilter(request, response);
            return;
        }

        // Get Token Jwt
        String jwt = authorizationHeader.split(" ")[1];
        // Get subject/username of token
        String username = jwtService.extractUsername(jwt);
        log.info("Extracted username from JWT: {}", username);
        // Set object authentication in security context holder
        User user = userService.findOneByUserName(username)
            .orElseThrow(()->new ObjectNotFoundException("User not found. Username.. : "+username));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,null ,user.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetails(request));
        
        SecurityContextHolder.getContext().setAuthentication(authToken);
        // Execute registers filters
        filterChain.doFilter(request, response);
    }
    
}
