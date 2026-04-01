package ru.itis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Смотрим:
 * @see org.springframework.security.core.Authentication
 * @see org.springframework.security.core.context.SecurityContext
 * @see org.springframework.security.core.context.SecurityContextHolder
 * @see org.springframework.security.web.context.SecurityContextHolderFilter
 * @see org.springframework.security.authentication.AuthenticationManager
 * @see org.springframework.security.authentication.AuthenticationProvider
 * @see org.springframework.security.authorization.AuthorizationManager
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 * @see org.springframework.security.core.userdetails.UserDetails
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @see org.springframework.security.web.FilterChainProxy
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/WEB-INF/**", "/css/**").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/user", "/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/user", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        return http.build();
    }
}
