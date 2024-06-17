package com.spring.security.service;

import com.spring.security.entity.UserCredential;
import com.spring.security.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired  // inject bean of UserCredentialRepository so that we can save the userCredential details in database
    private UserCredentialRepository repository;

    @Autowired  // inject bean of PasswordEncoder so that UserCredential password can be encrypted and stored
    private PasswordEncoder passwordEncoder;

    @Autowired  // inject bean of JwtService which is used to create and validate jwt token
    private JwtService jwtService;

    // method to register/save UserCredential entity values in database
    public String addUser(UserCredential credential){
        // before saving userCredentials in database, we have to encrypt password and then store it
        // password can be encrypted with PasswordEncoder. Define a bean of it in AuthConfig class

        credential.setPassword(passwordEncoder.encode(credential.getPassword()));   // encrypted password and again stored in password column
        repository.save(credential);    // userCredential details added to database

        return "User Added To System !!!";
    }

    // method to generate a jwt token for each user
    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    // method to validate a jwt token
    public void validateToken(String token){
        jwtService.validateToken(token);
    }



}
