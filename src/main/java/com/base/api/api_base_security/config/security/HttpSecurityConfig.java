package com.base.api.api_base_security.config.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.base.api.api_base_security.config.security.filter.JwtAuthenticationFilter;
import com.base.api.api_base_security.config.security.handler.CustomAccessDeniedHandler;
import com.base.api.api_base_security.config.security.handler.CustomAuthenticationEntryPoint;
import lombok.AllArgsConstructor;


import static org.springframework.security.config.Customizer.withDefaults;

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
            //.cors(Customizer.withDefaults()) // Enabled Cors Default
            .cors(withDefaults())
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

    @Profile({"local","dev"})
    @Bean
    CorsConfigurationSource defaultCorsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://www.google.com","http://127.0.0.1:5500"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Profile({"docker","dev"})
    @Bean
    CorsConfigurationSource dockerCorsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://client"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
