package com.example.authService.configurations;

import com.example.authService.filters.OpaqueTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    public static List<String> publicUrl = List.of("/api/v1/user/login", "/api/v1/user/registration");
    public static List<String> adminUrl = List.of("/api/v1/user/all");

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, OpaqueTokenFilter opaqueTokenFilter) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(publicUrl.toArray(new String[0])).permitAll()
                        .requestMatchers(adminUrl.toArray(new String[0])).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(opaqueTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
