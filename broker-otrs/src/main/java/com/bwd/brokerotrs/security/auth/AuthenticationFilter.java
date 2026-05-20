package com.bwd.brokerotrs.security.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

            if (scopes.contains("nms") || scopes.contains("cms") || scopes.contains("ticketing") ) {
                filterChain.doFilter(request, response);

                return;
            }
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
