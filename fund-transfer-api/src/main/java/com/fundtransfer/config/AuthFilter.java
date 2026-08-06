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
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        String authHeader =
                httpRequest.getHeader("Authorization");

        // CORS
        String origin = httpRequest.getHeader("Origin");

        // CORS Headers
        if (origin != null) {

            httpResponse.setHeader(
                    "Access-Control-Allow-Origin",
                    origin);

            httpResponse.setHeader(
                    "Access-Control-Allow-Credentials",
                    "true");

            httpResponse.setHeader(
                    "Access-Control-Allow-Methods",
                    "GET,POST,PUT,DELETE,OPTIONS");

            httpResponse.setHeader(
                    "Access-Control-Allow-Headers",
                    "Authorization,Content-Type");

        }

        // Allow browser preflight request
if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
    httpResponse.setStatus(HttpServletResponse.SC_OK);
    return;
}

        if (path.equals("/statement") ||
    path.startsWith("/api/users/") ||
    path.equals("/admin/login")) {

    chain.doFilter(request, response);
    return;
}

        if (path.equals("/api/auth/login")
        || path.equals("/api/auth/register")
        || path.equals("/api/auth/forgot-password")
        || path.equals("/admin/login")
        || path.startsWith("/admin/users")
        || path.startsWith("/api/users")
        || path.startsWith("/transactions")
        || path.startsWith("/transaction-pin")) {

    chain.doFilter(request, response);
    return;
}

        // Validate JWT Token
        if (!tokenService.isValidToken(authHeader)) {

            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");

            httpResponse.getWriter()
                    .write("{\"message\":\"Unauthorized\"}");

            return;
        }

        chain.doFilter(request, response);
    }
}