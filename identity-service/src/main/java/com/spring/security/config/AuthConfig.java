package com.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // the @EnableWebSecurity annotation is a powerful tool that enables developers to configure Spring Security for a web application.
public class AuthConfig {

    // create a bean of CustomUserDetailsService which will talk to db to validate the user and sent detail to
    // authenticationProvider which will send the details to AuthenticationManager
    @Bean
    public UserDetailsService userDetailsService(){

        return new CustomUserDetailsService();
    }

    // authorization
    // this bean if for SecurityFilterChain which is for authorization of users for
    // what endpoints we need to bypass and what endpoints need to be authenticated
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)   // disable csrf
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/auth/**").permitAll()
                        //.requestMatchers("/auth/register", "/auth/getToken", "/auth/validate").permitAll()   // permit all http requests with /products/welcome, /products/new, /products/authenticate
                        .anyRequest().authenticated()   // authenticate all other requests apart from /products/welcome, /products/new, /products/authenticate
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .build();
    }

    // create bean of PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // create a bean of AuthenticationProvider which will talk to UserDetailsService and get the details required
    // if we don't create this bean then we will not be able to get the access the endpoints
    @Bean
    public AuthenticationProvider authenticationProvider(){
        // AuthenticationProvider will talk to UserDetailsService and generate the UserDetails object and sent it to Authentication object
        // create object of DaoAuthenticationProvider and set userDetailsService() and PasswordEncoder() in DaoAuthenticationProvider
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService()); // set the userDetailsService in authentication provider
        authenticationProvider.setPasswordEncoder(passwordEncoder());   // set the password encoder in authentication provider

        return authenticationProvider;  // now we will be able to access the endpoints via authentication provider.
    }

    // define a bean of AuthenticationManager which is used to authenticate the userCredentials from database and then
    // only allow them to generate jwt token after validating the user is correct in database or not
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
