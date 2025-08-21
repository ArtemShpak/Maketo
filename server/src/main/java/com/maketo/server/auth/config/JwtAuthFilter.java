package com.maketo.server.auth.config;

import com.maketo.server.auth.application.port.out.JwtTokenPort;
import com.maketo.server.auth.application.port.out.VerifyTokenPort;
import com.maketo.server.auth.infrastructure.security.UserInfoDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final VerifyTokenPort verifyTokenPort;
    private final UserInfoDetailsService userDetailsService;

    public JwtAuthFilter(VerifyTokenPort verifyTokenPort,
                         UserInfoDetailsService userDetailsService) {
        this.verifyTokenPort = verifyTokenPort;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String token = extractBearerToken(request);

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            verifyTokenPort.verify(token, JwtTokenPort.TokenPurpose.AUTH).ifPresent(verified -> {
                UserDetails user = userDetailsService.loadUserByUsername(verified.subject());
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            });
        }
        chain.doFilter(request, response);
    }

    private String extractBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) return null;
        return header.substring(7);
    }
}