package com.base.api.api_base_security.config.security.handler;

import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

    final private AccessDeniedHandler accessDeniedHandler;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        
        accessDeniedHandler.handle(request, response, new AccessDeniedException("Access Denied."));
        
        /* 
        ApiError apiError = new ApiError();
        apiError.setBackendMessage(authException.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setMessage("No authentication credentials were found. Please log in to access this feature.");
        apiError.setTimestamp(LocalDateTime.now());
         
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        String apiErrorJson = objectMapper.writeValueAsString(apiError);
        
        response.getWriter().write(apiErrorJson);
        */
        
    }
    
}
