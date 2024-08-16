package com.atmosware.library_project.core.services;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Service;

@Service
public class SecurityManager implements SecurityService
{
    private static final String[] WHITE_LIST_URLS = {
            "/swagger-ui/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/api/auth/**",
            "/api/users/**",
            "/api/books/**",
            "/api/transactions/**"
    };

    @Override
    public HttpSecurity configureSecurity(HttpSecurity http) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF korumasını devre dışı bırakma
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.POST).permitAll()
                        .requestMatchers(HttpMethod.GET).permitAll()
                        .requestMatchers(WHITE_LIST_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .cors(AbstractHttpConfigurer::disable);
        return http;
    }
}
