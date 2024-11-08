package org.example.library.config;

import lombok.RequiredArgsConstructor;
import org.example.library.security.AuthEntryPointJwt;
import org.example.library.security.AuthTokenFilter;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthEntryPointJwt authEntryPointJwt;
    private final AuthTokenFilter authTokenFilter;

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
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
                .sessionManagement(AbstractHttpConfigurer::disable)
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
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
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }

}
