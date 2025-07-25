package com.example.authService.filters;

import com.example.authService.configurations.SecurityConfig;
import com.example.authService.enums.UserRole;
import com.example.authService.exceptions.ErrorResponse;
import com.example.authService.exceptions.TokenInvalidException;
import com.example.authService.exceptions.TokenIsExpiredException;
import com.example.authService.repositories.AuthTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
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
            response.sendError(403, "Need Auth");
            return;
//            throw new AccessDeniedException("Need Auth");
        }
        var tokenOpt = authTokenRepository.findById(UUID.fromString(authorizationHeader.substring(7)));
        if(tokenOpt.isEmpty()){
            response.sendError(401, "Token is invalid");
//            throw new TokenInvalidException("Token is invalid");

            return;
        }


        if(tokenOpt.get().getExpiresAt().isBefore(Instant.now())){
            authTokenRepository.deleteById(tokenOpt.get().getToken());
            response.sendError(401, "Token is expired");
            return;
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                tokenOpt.get().getUserEntity(),
                null,
                Collections.singletonList(tokenOpt.get().getUserEntity().getUserRole().toAuthority())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response,
                                   HttpStatus status,
                                   String error,
                                   String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());

        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                error,
                message
        );

        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
