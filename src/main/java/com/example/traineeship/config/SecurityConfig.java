package com.example.traineeship.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
public class SecurityConfig {

    // Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Redirect to "/" on login failure with ?error=true
    @Bean
    public AuthenticationFailureHandler customFailureHandler() {
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler("/");
        handler.setUseForward(false);
        handler.setDefaultFailureUrl("/?error=true");
        return handler;
    }

    // Security filter chain for HTTP security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/user/signup", "/user/save-user", "/css/**").permitAll()
                .requestMatchers("/student/**").hasRole("STUDENT")
                .requestMatchers("/company/**").hasRole("COMPANY")
                .requestMatchers("/professor/**").hasRole("PROFESSOR")
                .requestMatchers("/committee/**").hasRole("COMMITTEE")
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                //.loginPage("/login") // Optional: default login page
                .defaultSuccessUrl("/redirect-by-role", true)
                .failureHandler(customFailureHandler())
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}
