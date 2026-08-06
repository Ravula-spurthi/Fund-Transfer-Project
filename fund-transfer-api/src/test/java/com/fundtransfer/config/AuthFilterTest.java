package com.fundtransfer.config;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.fundtransfer.service.TokenService;

class AuthFilterTest {

    @Test
    void shouldRejectRequestWithoutToken() throws Exception {
        AuthFilter authFilter = new AuthFilter(new TokenService());
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/beneficiaries");
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicBoolean chainCalled = new AtomicBoolean(false);

        MockFilterChain chain = new MockFilterChain();
        authFilter.doFilter(request, response, (req, res) -> chainCalled.set(true));

        assertFalse(chainCalled.get());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    void shouldAllowRequestWithValidToken() throws Exception {
        AuthFilter authFilter = new AuthFilter(new TokenService());
        String token = new TokenService().generateToken(1L, "user@example.com");
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/beneficiaries");
        request.addHeader("Authorization", token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicBoolean chainCalled = new AtomicBoolean(false);

        authFilter.doFilter(request, response, (req, res) -> chainCalled.set(true));

        assertTrue(chainCalled.get());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void shouldAllowStatementRequestWithoutToken() throws Exception {
        AuthFilter authFilter = new AuthFilter(new TokenService());
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/statement");
        request.setQueryString("userId=1");
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicBoolean chainCalled = new AtomicBoolean(false);

        authFilter.doFilter(request, response, (req, res) -> chainCalled.set(true));

        assertTrue(chainCalled.get());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void shouldAllowProfileRequestWithoutToken() throws Exception {
        AuthFilter authFilter = new AuthFilter(new TokenService());
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/users/1");
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicBoolean chainCalled = new AtomicBoolean(false);

        authFilter.doFilter(request, response, (req, res) -> chainCalled.set(true));

        assertTrue(chainCalled.get());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void shouldAllowPreflightOptionsRequest() throws Exception {
        AuthFilter authFilter = new AuthFilter(new TokenService());
        MockHttpServletRequest request = new MockHttpServletRequest("OPTIONS", "/transactions/user/14");
        request.addHeader("Origin", "http://localhost:4200");
        request.addHeader("Access-Control-Request-Method", "GET");
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicBoolean chainCalled = new AtomicBoolean(false);

        authFilter.doFilter(request, response, (req, res) -> chainCalled.set(true));

        assertFalse(chainCalled.get());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void shouldAllowTransactionPinRequestsWithoutToken() throws Exception {
        AuthFilter authFilter = new AuthFilter(new TokenService());
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/transaction-pin/change");
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicBoolean chainCalled = new AtomicBoolean(false);

        authFilter.doFilter(request, response, (req, res) -> chainCalled.set(true));

        assertTrue(chainCalled.get());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
