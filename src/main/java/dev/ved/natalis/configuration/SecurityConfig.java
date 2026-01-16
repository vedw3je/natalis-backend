package dev.ved.natalis.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF (needed for Postman / mobile apps)
                .csrf(csrf -> csrf.disable())

                // Allow ALL requests without authentication
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}