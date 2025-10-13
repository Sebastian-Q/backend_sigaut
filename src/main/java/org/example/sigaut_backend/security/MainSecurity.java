package org.example.sigaut_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MainSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva protección CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // Permite todas tus rutas /api/*
                        .anyRequest().permitAll()               // Permite todo lo demás
                )
                .httpBasic(httpBasic -> httpBasic.disable()) // Desactiva login básico
                .formLogin(form -> form.disable());          // Desactiva login por formulario

        return http.build();
    }
}