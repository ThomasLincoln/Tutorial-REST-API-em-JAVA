package io.thomaslincoln.restapi.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.thomaslincoln.restapi.security.JWTAuthenticationFilter;
import io.thomaslincoln.restapi.security.JWTAuthorizationFilter;
import io.thomaslincoln.restapi.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  @Autowired
  private JWTUtil jwtUtil;

  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsService userDetailsService;

  private static final String[] PUBLIC_MATCHERS = {
      "/"
  };

  private static final String[] PUBLIC_MATCHERS_POST = {
      "/usuario",
      "/receita",
      "/ingrediente"
  };

  private static final String[] PUBLIC_MATCHERS_GET = {
      "/usuario/**",
      "/receita/**",
      "/ingrediente/**"
  };

  private static final String[] PUBLIC_MATCHERS_DELETE = {
      "/usuario/**",
      "/receita/**",
      "/ingrediente/**"
  };

  private static final String[] PUBLIC_MATCHERS_PUT = {
      "/usuario/**",
      "/receita/**",
      "/ingrediente/**"
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(cors -> cors.disable());
    http.csrf(csrf -> csrf.disable());

    
    http.authorizeHttpRequests(
      auth -> auth
      .requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
      .requestMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
      .requestMatchers(HttpMethod.PUT, PUBLIC_MATCHERS_PUT).permitAll()
      .requestMatchers(HttpMethod.DELETE, PUBLIC_MATCHERS_DELETE).permitAll()
      .requestMatchers(PUBLIC_MATCHERS).permitAll()
      .anyRequest().authenticated());

      http.authenticationManager(authenticationManager);
      http.addFilter(new JWTAuthenticationFilter(this.authenticationManager, this.jwtUtil));
      http.addFilter(new JWTAuthorizationFilter(this.authenticationManager, this.jwtUtil, this.userDetailsService));
      http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
      return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
    configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
