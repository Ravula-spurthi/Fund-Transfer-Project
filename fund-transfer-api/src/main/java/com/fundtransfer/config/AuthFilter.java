package com.fundtransfer.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fundtransfer.service.TokenService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter implements Filter {

    private final TokenService tokenService;

    public AuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authHeader = httpRequest.getHeader("Authorization");
        String path = httpRequest.getRequestURI();
        String origin = httpRequest.getHeader("Origin");

        if (origin != null) {
            httpResponse.setHeader("Access-Control-Allow-Origin", origin);
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type");
        }

        if (path.startsWith("/api/auth/") || "OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpStatus.OK.value());
            chain.doFilter(request, response);
            return;
        }

        if (path.equals("/statement") || path.startsWith("/api/users/")) {
            chain.doFilter(request, response);
            return;
        }

        if (!tokenService.isValidToken(authHeader)) {
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"message\":\"Unauthorized\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}
