package com.test.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

//@Configuration
public class SecurityConfig {

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        jdbcUserDetailsManager.setUsersByUsernameQuery(
//                "select email, password, enabled from employee where email=?");
//
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
//                "select user_email, role from roles where user_email=?");
//
//        return jdbcUserDetailsManager;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests(configurer ->
//                        configurer
//                                .requestMatchers("/").permitAll()
//                                .requestMatchers("/login").permitAll()
//                                .requestMatchers("/employees/show-form-for-add",
//                                        "/employees/save").hasRole("ADMIN")
//                                .requestMatchers("/employees/**").hasAnyRole("USER", "ADMIN")
//                                .anyRequest().authenticated()
//                )
//                .formLogin(form ->
//                        form
//                                .loginPage("/login")
//                                .loginProcessingUrl("/authenticate")
//                                .defaultSuccessUrl("/employees/list", true)
//                                .permitAll()
//                )
//                .logout(LogoutConfigurer::permitAll)
//                .exceptionHandling(configurer ->
//                        configurer.accessDeniedPage("/access-denied"))
//        ;
//
//        return http.build();
//    }

}
