package com.test.springboot.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.springboot.security.filters.JsonUsernamePasswordAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RestSecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select email, password, enabled from employee where email=?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_email, role from roles where user_email=?");

        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        JsonUsernamePasswordAuthFilter jsonFilter = new JsonUsernamePasswordAuthFilter(authManager);
        jsonFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        jsonFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

        http
                .securityContext(securityContext ->
                        securityContext
                                .requireExplicitSave(false)
                )
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/", "/api/auth/login", "/api/auth/logout", "/hello", "/api/employees/check-session").permitAll()
                                .requestMatchers("/api/employees", "/api/employees/**").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated()
                )
                .addFilterAt(jsonFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout
                                .logoutUrl("/api/auth/logout")
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    response.setStatus(HttpStatus.OK.value());
                                    response.setContentType("application/json");
                                    response.getWriter().write("{\"message\":\"Logout successful\"}");
                                })
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                )
                .exceptionHandling(exceptions ->
                        exceptions
                                .accessDeniedHandler(accessDeniedHandler())
                                .authenticationEntryPoint(authenticationEntryPoint())
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");

            Map<String, Object> data = new HashMap<>();
            data.put("success", true);
            data.put("message", "Login successful");
            data.put("username", authentication.getName());
            data.put("authorities", authentication.getAuthorities());

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(data));
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            Map<String, Object> data = new HashMap<>();
            data.put("success", false);
            data.put("message", "Invalid username or password");
            data.put("error", exception.getMessage());

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(data));
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");

            Map<String, Object> data = new HashMap<>();
            data.put("success", false);
            data.put("message", "Access denied");
            data.put("error", "You don't have permission to access this resource");

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(data));
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            Map<String, Object> data = new HashMap<>();
            data.put("success", false);
            data.put("message", "Authentication required");
            data.put("error", "Please login to access this resource");

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(data));
        };
    }
}
