package com.example.Titan.config;

import jakarta.servlet.FilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityConfig {
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security
                .cors()
                .and()
                .authorizeHttpRequests(auth ->
                                auth.anyRequest().permitAll())
                .csrf().disable();
        return security.build();
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }
}
