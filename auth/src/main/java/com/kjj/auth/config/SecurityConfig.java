package com.kjj.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    @Value("${my.actuator.path}") private String actuatorPath;

    /**
     * Spring Security 설정을 위한 빈
     *
     * @param http  HttpSecurity 객체
     * @return SecurityFilterChain 빈
     * @throws Exception http.sessionManagement()에서 throw 하는 예외
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter)
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable();

        http.authorizeHttpRequests()
                .requestMatchers("/v1/auth/check/jwt").permitAll()
                .requestMatchers(actuatorPath + "health").permitAll()
                .anyRequest().authenticated();

        http.headers().xssProtection()
                .and().contentSecurityPolicy("script-src 'self'");

        return http.build();
    }
}