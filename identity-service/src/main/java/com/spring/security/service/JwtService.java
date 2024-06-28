package com.spring.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service    // this JwtService class is used to create and validate jwt token using Jwts.parserBuilder()
public class JwtService {
    // 256 bit encrypted key generated to create and validate jwt token
    private static final String SECRET = "b5eee28c0ac1436d229a464548d5e6ae422369caa5aa1c1659c81e499922f982";

    // method to validate the token
    public void validateToken(final String token) {
        Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //    =========================================================================
    // create JWT TOKEN
//    =========================================================================
    // note: in JWT all the components(i.e header, payload, signature) are called as claims
    // we need to generate claims for each component in jwt
    // for that only we have use Map<String, Object> inorder to store each component
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }



    public String createToken(Map<String, Object> claims, String username) {
        // this method will create jwt token
        // to use jwt add dependency of jwt in pom.xml

        return Jwts.builder()
                .setClaims(claims)  // set claims
                .setSubject(username)   // set the username in payload
                .setIssuedAt(new Date(System.currentTimeMillis()))  // current time of system to issue jwt token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))   // set expiration time to 30 minutes
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();  // provide signKey, signatureAlgorithm (.compact() will give you the jwt string back)

    }

    // in getSignKey() method we will provide the encoded base 64 header and encoded base 64 signature for jwt
    private Key getSignKey() {
        // create a byte array as we are converting our key into base 64 byte encoder
        // used this link to generate encryption key https://asecuritysite.com/encryption/plain

        byte[] keyBytes = Decoders.BASE64.decode(SECRET); // secret is my own encrypted secret
        return Keys.hmacShaKeyFor(keyBytes);   // this will give you the sign key based on the secret


    }


}
