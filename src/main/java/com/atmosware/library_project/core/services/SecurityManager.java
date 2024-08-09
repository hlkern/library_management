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
            "/api/books/**"
    };

    @Override
    public HttpSecurity configureSecurity(HttpSecurity http) throws Exception {


//        http.authorizeHttpRequests(x-> x
//                .requestMatchers(WHITE_LIST_URLS).permitAll()
//                //.requestMatchers(HttpMethod.POST, "/api/v1/cars").hasAnyAuthority(Roles.ADMIN, Roles.MODERATOR)
//                .anyRequest().authenticated()
//        );
//        return http;

        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF korumasını devre dışı bırakma
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.POST).permitAll()
                        .requestMatchers(WHITE_LIST_URLS).permitAll() // Bu URL'leri serbest bırakma
                        .anyRequest().authenticated() // Diğer tüm istekler kimlik doğrulama gerektirir
                )
                .cors(AbstractHttpConfigurer::disable);
        return http;
    }
}
