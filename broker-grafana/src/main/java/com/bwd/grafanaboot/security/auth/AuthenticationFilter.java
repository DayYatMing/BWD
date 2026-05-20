package com.bwd.grafanaboot.security.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String scopeHeader = request.getHeader("X-Consumer-Groups");

        if (scopeHeader != null) {
            List<String> scopes = Arrays.stream(scopeHeader.split(","))
                    .map(String::trim)
                    .toList();

            if (scopes.contains("cms")) {
                filterChain.doFilter(request, response);

                return;
            }
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
