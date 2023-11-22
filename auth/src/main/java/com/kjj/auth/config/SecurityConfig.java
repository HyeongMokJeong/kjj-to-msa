package com.kjj.auth.config;

import com.kjj.auth.security.JwtTemplate;
import com.kjj.auth.security.JwtUtil;
import com.kjj.auth.security.filter.ExceptionHandlerBeforeJwtAuth;
import com.kjj.auth.security.filter.JwtAuthFilter;
import com.kjj.auth.security.filter.JwtRefreshFilter;
import com.kjj.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final JwtUtil jwtUtil;
    private final JwtTemplate jwtTemplate = new JwtTemplate();
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    /**
     * Spring Security 설정을 무시하기 위한 빈
     *
     * @return WebSecurityCustomizer 빈
     */
    @Bean
    public WebSecurityCustomizer customizer() {
        return web -> web.ignoring().requestMatchers(
                "/apis",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/favicon.ico"
        );
    }

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

        http.addFilterBefore(new JwtRefreshFilter("/api/user/login/refresh", userService, jwtUtil, jwtTemplate), JwtAuthFilter.class)
                .addFilterBefore(new ExceptionHandlerBeforeJwtAuth(), JwtAuthFilter.class)
                .addFilter(new JwtAuthFilter(authenticationManager, userService, jwtTemplate, jwtUtil));

        http.authorizeHttpRequests()
                .requestMatchers("/api/image").permitAll()
                .requestMatchers("/api/user/login/**").permitAll()
                .requestMatchers("/api/user/order/**/qr").permitAll()
                .requestMatchers("/api/manager/login/**").permitAll()
                .requestMatchers("/api/user/**").hasAnyRole("USER", "MANAGER")
                .requestMatchers("/api/manager/**").hasRole("MANAGER")
                .anyRequest().authenticated();

        http.headers().xssProtection()
                .and().contentSecurityPolicy("script-src 'self'");

        return http.build();
    }
}