package com.spring.security.controller;

import com.spring.security.dto.AuthRequest;
import com.spring.security.entity.UserCredential;
import com.spring.security.service.AuthService;
import com.spring.security.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    // inject bean of AuthService which contains all business logic
    @Autowired
    private AuthService service;

    // inject a bean of AuthenticationManager which is used to authenticate username, password details
    // from database and then allow them to do further actions like generate jwt token
    @Autowired
    private AuthenticationManager authenticationManager;

    // register/save a user
    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user){
        return service.addUser(user);
    }

    // endpoint to get a jwt token

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){

        // authenticate if userName and password is correct in db with authenticationManager
        // and only allow if userName and password is correct
//        =============================================================
        // note: AuthenticationManager class will not be able to talk to db directly. so we need UserDetailsService implementation class
        // which will talk to db and validate the user and send the user details to AuthenticationProvider which will validate the user details
        // and send the user details to AuthenticationManager for validation.
        // create a bean of CustomUserDetailsService which implements UserDetailsService interface to talk to db and validate user.

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword())
        );

        return service.generateToken(authRequest.getUsername());

    }


    // endpoint to validate a jwt token
    // we will give the jwt token in request parameter in the url while calling validateToken endpoint
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        service.validateToken(token);
        return "Token is valid !!!";
    }

}
