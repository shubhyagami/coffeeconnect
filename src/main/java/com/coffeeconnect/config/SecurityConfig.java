package com.coffeeconnect.config;

import com.coffeeconnect.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.ignoringRequestMatchers(
                new AntPathRequestMatcher("/h2-console/**"),
                new AntPathRequestMatcher("/ws/**"),
                new AntPathRequestMatcher("/api/**"),
                new AntPathRequestMatcher("/uploads/**")
            ))
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/"), 
                                 new AntPathRequestMatcher("/home"),
                                 new AntPathRequestMatcher("/register"),
                                 new AntPathRequestMatcher("/login"),
                                 new AntPathRequestMatcher("/verify-domain"),
                                 new AntPathRequestMatcher("/css/**"),
                                 new AntPathRequestMatcher("/js/**"),
                                 new AntPathRequestMatcher("/images/**"),
                                 new AntPathRequestMatcher("/h2-console/**"),
                                 new AntPathRequestMatcher("/uploads/**"),
                                 new AntPathRequestMatcher("/ws/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/super-admin/**")).hasAuthority("ROLE_SUPER_ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .rememberMe(remember -> remember
                .key("coffeeconnect-secret-key")
                .tokenValiditySeconds(86400 * 7)
                .userDetailsService(userDetailsService)
            )
            .sessionManagement(session -> session
                .sessionFixation().migrateSession()
                .maximumSessions(1)
                .expiredUrl("/login?expired=true")
            )
            .userDetailsService(userDetailsService);
        return http.build();
    }
}
