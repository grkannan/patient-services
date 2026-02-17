package org.gatex.patientservices.config;

import lombok.RequiredArgsConstructor;
import org.gatex.patientservices.security.HeaderAuthenticationFilter;
import org.gatex.patientservices.security.RestAccessDeniedHandler;
import org.gatex.patientservices.security.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final HeaderAuthenticationFilter headerAuthenticationFilter;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final RestAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                .authorizeHttpRequests(auth -> auth

                        // Public endpoints
                        .requestMatchers("/api/auth/**").permitAll()

                        // ADMIN only
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // RECEPTION & ADMIN
                        .requestMatchers("/api/reception/**")
                        .hasAnyRole("ADMIN", "RECEPTION")

                        // DOCTOR & ADMIN
                        .requestMatchers("/api/doctor/**")
                        .hasAnyRole("ADMIN", "DOCTOR")

                        // PATIENT APIs (all authenticated users)
                        .requestMatchers("/api/patients/**")
                        .hasAnyRole("ADMIN", "RECEPTION", "DOCTOR")

                        .requestMatchers("/api/billing/**")
                        .hasAnyRole("ADMIN", "BILLING")

                        // Everything else must be authenticated
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        headerAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}