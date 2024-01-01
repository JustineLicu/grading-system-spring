package com.cvsuimus.bsit4b.security;

import java.util.Arrays;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;

import com.cvsuimus.bsit4b.authentication.AuthenticationService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  public static String frontendUrl = "http://localhost:3000";

  @Bean
  BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList(frontendUrl));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    // configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            requests -> requests.requestMatchers("/api/v*/auth/**").permitAll()
                .anyRequest().authenticated())
        .formLogin(login -> login.loginProcessingUrl("/api/v1/auth/login")
            .defaultSuccessUrl(frontendUrl).failureUrl(frontendUrl + "/?error"))
        .logout(logout -> logout.logoutUrl("/api/v1/auth/logout"))
        .exceptionHandling(handling -> handling.authenticationEntryPoint(
            (request, response, authException) -> response.sendError(401)));

    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationService authenticationService, BCryptPasswordEncoder bCryptPasswordEncoder) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(authenticationService);
    authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);

    return new ProviderManager(authenticationProvider);
  }
}
