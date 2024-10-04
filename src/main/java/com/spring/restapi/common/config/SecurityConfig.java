package com.spring.restapi.common.config;

import com.spring.restapi.auth.filter.JwtFilter;
import com.spring.restapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final JwtFilter jwtFilter;

    private static final List<String> PUBLIC_API_ENDPOINTS = List.of(
            "/api/v1/login/**",
            "/api/v1/info/**",
            "/api/v1/non-secure/**",
            "/api/v1/users/register/**"
    );

    private static final List<String> ADMIN_API_ENDPOINTS = List.of(
            "/api/v1/admin/**",
            "/api/v1/user/**"
    );

    private static final List<String> HR_MANAGER_API_ENDPOINTS = List.of(
            "/api/v1/hr/**"
    );

    private static final List<String> FINANCE_MANAGER_API_ENDPOINTS = List.of(
            "/api/v1/finance/**"
    );

    private static final List<String> INVENTORY_MANAGER_API_ENDPOINTS = List.of(
            "/api/v1/inventory/**"
    );

    private static final List<String> SALES_MANAGER_API_ENDPOINTS = List.of(
            "/api/v1/sales/**"
    );

    private static final List<String> MANUFACTURING_MANAGER_API_ENDPOINTS = List.of(
            "/api/v1/manufacturing/**"
    );

    private static final List<String> PROJECT_MANAGER_API_ENDPOINTS = List.of(
            "/api/v1/projects/**"
    );

    private static final List<String> CUSTOMER_SUPPORT_API_ENDPOINTS = List.of(
            "/api/v1/support/**"
    );

    private static final List<String> PROCUREMENT_MANAGER_API_ENDPOINTS = List.of(
            "/api/v1/procurement/**"
    );

    private static final List<String> IT_ADMIN_API_ENDPOINTS = List.of(
            "/api/v1/it/**"
    );

    private static final List<String> USER_API_ENDPOINTS = List.of(
            "/api/v1/user/**"
    );

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var roleEndpointsMap = Map.ofEntries(
                Map.entry("PUBLIC", PUBLIC_API_ENDPOINTS),
                Map.entry("ADMIN", ADMIN_API_ENDPOINTS),
                Map.entry("HR_MANAGER", HR_MANAGER_API_ENDPOINTS),
                Map.entry("FINANCE_MANAGER", FINANCE_MANAGER_API_ENDPOINTS),
                Map.entry("INVENTORY_MANAGER", INVENTORY_MANAGER_API_ENDPOINTS),
                Map.entry("SALES_MANAGER", SALES_MANAGER_API_ENDPOINTS),
                Map.entry("MANUFACTURING_MANAGER", MANUFACTURING_MANAGER_API_ENDPOINTS),
                Map.entry("PROJECT_MANAGER", PROJECT_MANAGER_API_ENDPOINTS),
                Map.entry("CUSTOMER_SUPPORT", CUSTOMER_SUPPORT_API_ENDPOINTS),
                Map.entry("PROCUREMENT_MANAGER", PROCUREMENT_MANAGER_API_ENDPOINTS),
                Map.entry("IT_ADMIN", IT_ADMIN_API_ENDPOINTS),
                Map.entry("USER", USER_API_ENDPOINTS)
        );

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(PUBLIC_API_ENDPOINTS.toArray(String[]::new)).permitAll();
                    roleEndpointsMap.forEach((role, endpoints) ->
                            request.requestMatchers(endpoints.toArray(String[]::new)).hasRole(role)
                    );
                    request.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
