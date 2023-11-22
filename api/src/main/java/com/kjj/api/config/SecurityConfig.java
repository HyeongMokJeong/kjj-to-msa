package com.kjj.api.config;

import com.kjj.api.auth.JwtTemplate;
import com.kjj.api.auth.JwtUtil;
import com.kjj.api.auth.login.filter.KeycloakLoginFilterV2;
import com.kjj.api.auth.login.filter.LoginFilterV2;
import com.kjj.api.auth.login.filter.util.CreateTokenInterfaceUserImpl;
import com.kjj.api.auth.monitor.IpCheckFilter;
import com.kjj.api.exception.handler.filter.ExceptionHandlerBeforeJwtAuth;
import com.kjj.api.exception.handler.filter.ExceptionHandlerBeforeKeycloak;
import com.kjj.api.exception.handler.filter.ExceptionHandlerBeforeUsernamePassword;
import com.kjj.api.external.KeycloakProperties;
import com.kjj.api.service.user.sso.UserSsoService;
import com.kjj.api.service.user.user.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${my.server.address}") private String address;
    @Value("${my.prometheus.path}") private String prometheusPath;
    @Value("${my.actuator.path}") private String actuatorPath;

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final Keycloak keycloak;
    private final JwtTemplate jwtTemplate = new JwtTemplate();
    private final UserService userService;
    private final UserSsoService userSsoService;
    private final AuthenticationManager authenticationManager;
    private final KeycloakProperties properties;

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
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable();

        http.addFilterBefore(new IpCheckFilter(prometheusPath, new String[]{address}), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerBeforeUsernamePassword(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new LoginFilterV2("/api/user/login/id", authenticationManager, new CreateTokenInterfaceUserImpl(), jwtUtil, jwtTemplate), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new LoginFilterV2("/api/manager/login/id", authenticationManager, new CreateTokenInterfaceUserImpl(), jwtUtil, jwtTemplate), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerBeforeKeycloak(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new KeycloakLoginFilterV2("/api/user/login/keycloak", restTemplate, properties, jwtUtil, jwtTemplate, userService, userSsoService, keycloak), UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers(prometheusPath).permitAll()
                .requestMatchers(actuatorPath + "health").permitAll()
                .anyRequest().authenticated();

        http.headers().xssProtection()
                .and().contentSecurityPolicy("script-src 'self'");

        return http.build();
    }
}