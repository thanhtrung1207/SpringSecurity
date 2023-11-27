package com.example.ecommerce.api.security;


import com.example.ecommerce.Service.Impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.stereotype.Component;


@Configuration
public class WebSecurityConfig {

    private JwtAuthFilter jwtRequestFilter;

    public WebSecurityConfig(JwtAuthFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Filter chain to configure security.
     * @param http The security object.
     * @return The chain built.
     * @throws Exception Thrown on error configuring.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        // We need to make sure our authentication filter is run before the http request filter is run.
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        http.authorizeRequests()
                // Specific exclusions or rules.
                .requestMatchers("/product", "/auth/register", "/auth/login","/auth/me").permitAll()
                // Everything else should be authenticated.
                .anyRequest().authenticated();

        return http.build();
    }
}
