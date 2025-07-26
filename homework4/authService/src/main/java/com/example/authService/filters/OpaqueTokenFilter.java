package com.example.authService.filters;

import com.example.authService.configurations.SecurityConfig;
import com.example.authService.exceptions.TokenInvalidException;
import com.example.authService.exceptions.TokenIsExpiredException;
import com.example.authService.repositories.AuthTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

@Service
public class OpaqueTokenFilter extends OncePerRequestFilter {

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        System.out.println(requestUri);

        if (SecurityConfig.publicUrl.stream().anyMatch(requestUri::startsWith)) {
            System.out.println("PUBLIC URL: " + requestUri);
            filterChain.doFilter(request, response);
            return;
        }
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new AccessDeniedException("Need Auth");
        }
        var tokenOpt = authTokenRepository.findById(UUID.fromString(authorizationHeader.substring(7)));
        if(tokenOpt.isEmpty()){
            throw new TokenInvalidException("Token is invalid");
        }


        if(tokenOpt.get().getExpiresAt().isBefore(Instant.now())){
            authTokenRepository.deleteById(tokenOpt.get().getToken());
            throw new TokenIsExpiredException("Token is expired");
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                tokenOpt.get().getUserEntity(),
                null,
                Collections.singletonList(tokenOpt.get().getUserEntity().getUserRole().toAuthority())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

}
