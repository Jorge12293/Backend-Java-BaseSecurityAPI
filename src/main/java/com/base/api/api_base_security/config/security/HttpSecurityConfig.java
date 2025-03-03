package com.base.api.api_base_security.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.base.api.api_base_security.config.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
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
            //.authorizeHttpRequests(authReqConfig->buildRequestMatchers(authReqConfig))
            .build();
        return filterChain;    
    }

    /*
    private void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        // Authorization Publics    
        authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();
        authReqConfig.anyRequest().authenticated();
    }
    */
    /*
    private void buildRequestMatchersV2(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        // Authorization products
        authReqConfig.requestMatchers(HttpMethod.GET,"/products")
            .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET,"/products/[0-9]*")) // Expression Regular
        //authReqConfig.requestMatchers(HttpMethod.GET,"/products/{productId}")
            .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name());
        
        authReqConfig.requestMatchers(HttpMethod.POST,"/products")
            .hasAnyRole(Role.ADMINISTRATOR.name());
        
        authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}")
            .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name());
        
        authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}/disabled")
            .hasAnyRole(Role.ADMINISTRATOR.name());
        
        // Authorization categories
        authReqConfig.requestMatchers(HttpMethod.GET,"/categories")
            .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.GET,"/categories/{categoryId}")
            .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name());
        
        authReqConfig.requestMatchers(HttpMethod.POST,"/categories")
            .hasAnyRole(Role.ADMINISTRATOR.name());
        
        authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}")
            .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name());
        
        authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}/disabled")
            .hasAnyRole(Role.ADMINISTRATOR.name());
           
        // Authorization Auth
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/profile")
            .hasAnyRole(Role.ADMINISTRATOR.name(),Role.ASSISTANT_ADMINISTRATOR.name(),Role.CUSTOMER.name());

        // Authorization Publics    
        authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();

        authReqConfig.anyRequest().authenticated();
    }
    */
}
