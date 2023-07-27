package org.bahmni.sms.web.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TokenProvider {

    private static final String SECRET_KEY = "xaatkPsUxe7ctyILU9IfkFVauqLdDyugc05Gj7B452wm1nxIqjjzPf9jGdEWLnXVBV1o87Il4gH4SobdlHBHzA==";

    public String generateToken() {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setIssuer("Bahmni")
                .setIssuedAt(new Date())
                .setExpiration(null)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}