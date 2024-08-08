package com.atmosware.library_project.core.services;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
            "/api/users/**"
    };

    @Override
    public HttpSecurity configureSecurity(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(x-> x
                .requestMatchers(WHITE_LIST_URLS).permitAll()
                .requestMatchers(HttpMethod.GET).permitAll()
                //.requestMatchers(HttpMethod.POST, "/api/v1/brands").hasAnyAuthority(Roles.ADMIN, Roles.MODERATOR)
                //.requestMatchers(HttpMethod.POST, "/api/v1/cars").hasAnyAuthority(Roles.ADMIN, Roles.MODERATOR)
                //TODO: KISITLAMAK İSTEDİĞİMİZ ENDPOİNTLERİ BURDA BELİRLE
                .anyRequest().authenticated()
        );
        return http;
    }
}
