package org.example.library.config;

import lombok.RequiredArgsConstructor;
import org.example.library.security.AuthEntryPointJwt;
import org.example.library.security.AuthTokenFilter;
import org.example.library.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${spring.security.rememberMe.key}")
    private String rememberMeKey;

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(
            HttpSecurity http,
            AuthEntryPointJwt authEntryPointJwt,
            AuthTokenFilter authTokenFilter
    ) throws Exception {
        return http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(
                        authz ->
                                authz
                                        .requestMatchers(
                                                "/swagger-ui/**",
                                                "/swagger-ui.html",
                                                "/v3/api-docs/**"
                                        ).permitAll()
                )
                .authorizeHttpRequests(
                        authz ->
                                authz
                                        .requestMatchers("/api/login", "/api/signin").permitAll()
                                        .anyRequest().authenticated()
                )
                .exceptionHandling(
                        eh ->
                                eh.authenticationEntryPoint(authEntryPointJwt)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sm ->
                                sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(authTokenFilter, BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(withDefaults())
                .authorizeHttpRequests(
                        authz ->
                                authz
                                        .requestMatchers(
                                                "/swagger-ui/**",
                                                "/swagger-ui.html",
                                                "/v3/api-docs/**"
                                        ).permitAll()
                )
                .authorizeHttpRequests(
                        authz ->
                                authz
                                        .requestMatchers("/", "/login", "/signin").permitAll()
                                        .anyRequest().authenticated()
                )
                .formLogin(
                      /*  form ->
                                form
                                        .loginPage("/login")
                                        .permitAll()*/
                        withDefaults()
                )
                .logout(
                        logout ->
                                logout
                                        .logoutSuccessUrl("/login")
                                        .deleteCookies("JSESSIONID")
                                        .permitAll()
                )
                .rememberMe(
                        rememberMe ->
                                rememberMe.key(rememberMeKey)
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        var daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            DaoAuthenticationProvider daoAuthenticationProvider,
            JwtTokenProvider jwtTokenProvider
    ) {
        return new ProviderManager(List.of(jwtTokenProvider, daoAuthenticationProvider));
    }

}
