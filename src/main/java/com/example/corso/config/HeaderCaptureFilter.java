package com.example.corso.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HeaderCaptureFilter implements Filter {
    private static final ThreadLocal<String> authHolder = new ThreadLocal<>();

    public static String getAuth() {
        return authHolder.get();
    }

    @Override
    public void doFilter(
            ServletRequest req,
            ServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        // grab the Authorization header
        authHolder.set(request.getHeader(HttpHeaders.AUTHORIZATION));
        try {
            chain.doFilter(req, res);
        } finally {
            authHolder.remove();
        }
    }
}
