package com.programming.blog.maliansdevelopersblog.Security;


import com.programming.blog.maliansdevelopersblog.Exception.SpringBlogException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;



@Service
public class JwtProvider {
    private KeyStore keyStore;
  // Key key =Keys.secretKeyFor(SignatureAlgorithm.HS512);
   // SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    @PostConstruct
    public void init() {
       // key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/devblog.jks");
            keyStore.load(resourceAsStream, "blog2020".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e){
            throw new SpringBlogException("Exception occured while loading keystore");
        }

    }
    public String generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("devblog", "blog2020".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
            throw new SpringBlogException("Exception occured while loading keystore");
        }

    }

    public boolean validateToken(String jwt){
        Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("devblog").getPublicKey();
        }catch (KeyStoreException e){
            throw new SpringBlogException("Exception occured while loading keystore");
        }
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
