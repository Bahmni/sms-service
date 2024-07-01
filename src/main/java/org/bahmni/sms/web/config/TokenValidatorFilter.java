package org.bahmni.sms.web.config;

import org.bahmni.sms.web.security.TokenValidator;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class TokenValidatorFilter extends OncePerRequestFilter {

    private  TokenValidator tokenValidator;


    public TokenValidatorFilter(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isValidToken = false;

        String AuthHeader = request.getHeader("Authorization");
        if (AuthHeader != null && AuthHeader.startsWith("Bearer ")) {
            String token = AuthHeader.substring(7);
            isValidToken = tokenValidator.validateToken(token);
        }
        if (!isValidToken) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        filterChain.doFilter(request,response);
    }
}
