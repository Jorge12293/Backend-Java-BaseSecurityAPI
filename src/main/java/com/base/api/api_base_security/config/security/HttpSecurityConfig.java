package com.base.api.api_base_security.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.base.api.api_base_security.config.security.filter.JwtAuthenticationFilter;
import com.base.api.api_base_security.config.security.handler.CustomAccessDeniedHandler;
import com.base.api.api_base_security.config.security.handler.CustomAuthenticationEntryPoint;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class HttpSecurityConfig {

    final private AuthenticationProvider authenticationProvider;
    final private JwtAuthenticationFilter jwtAuthenticationFilter;
    final private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    final private CustomAccessDeniedHandler customAccessDeniedHandler;
    final private AuthorizationManager<RequestAuthorizationContext> authorizationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        SecurityFilterChain filterChain = http
            .csrf(csrfConfig->csrfConfig.disable())
            .sessionManagement(ssMagConfig->ssMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            //.authorizeHttpRequests(authReqConfig->buildRequestMatchersV2(authReqConfig))
            .authorizeHttpRequests(authReqConfig->{ 
                authReqConfig.anyRequest().access(authorizationManager);
            })
            .exceptionHandling(exceptionConfig->{
                exceptionConfig.authenticationEntryPoint(customAuthenticationEntryPoint);
                exceptionConfig.accessDeniedHandler(customAccessDeniedHandler);
            })
            .build();
        return filterChain;    
    }

    /*
    private void buildRequestMatchersV2(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        // Authorization products
        authReqConfig.requestMatchers(HttpMethod.GET,"/products")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET,"/products/[0-9]*")) // Expression Regular
        //authReqConfig.requestMatchers(HttpMethod.GET,"/products/{productId}")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        
        authReqConfig.requestMatchers(HttpMethod.POST,"/products")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name());
        
        authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        
        authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}/disabled")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name());
        
        // Authorization categories
        authReqConfig.requestMatchers(HttpMethod.GET,"/categories")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.GET,"/categories/{categoryId}")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        
        authReqConfig.requestMatchers(HttpMethod.POST,"/categories")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name());
        
        authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        
        authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}/disabled")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name());
           
        // Authorization Auth
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/profile")
            .hasAnyRole(RoleEnum.ADMINISTRATOR.name(),RoleEnum.ASSISTANT_ADMINISTRATOR.name(),RoleEnum.CUSTOMER.name());

        // Authorization Publics    
        authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();

        authReqConfig.anyRequest().authenticated();
    }
    */
     /*
    private void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        // Authorization Publics    
        authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();
        authReqConfig.anyRequest().authenticated();
    }
    */
}
