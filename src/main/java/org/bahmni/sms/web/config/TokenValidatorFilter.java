package org.bahmni.sms.web.config;

import org.bahmni.sms.web.security.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@Order(Ordered.HIGHEST_PRECEDENCE)
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
