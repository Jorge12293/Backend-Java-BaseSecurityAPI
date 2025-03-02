package com.base.api.api_base_security.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.base.api.api_base_security.config.filter.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class HttpSecurityConfig {

    final private AuthenticationProvider authenticationProvider;
    final private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        SecurityFilterChain filterChain = http
            .csrf(csrfConfig->csrfConfig.disable())
            .sessionManagement(ssMagConfig->ssMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authReqConfig->{
                authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
                authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
                authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();

                authReqConfig.anyRequest().authenticated();
            })
            .build();
        return filterChain;    
    }
}
