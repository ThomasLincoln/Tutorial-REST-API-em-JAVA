package io.thomaslincoln.restapi.security;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
  private JWTUtil jwtUtil;
  private UserDetailsService userDetailsService;

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
      JWTUtil jwtUtil, UserDetailsService userDetailsService) {
    super(authenticationManager);
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String token) {
    if (this.jwtUtil.isValidToken(token)) {
      String username = this.jwtUtil.getUsername(token);
      UserDetails user = this.userDetailsService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          username, null,
          user.getAuthorities());
      return authenticationToken;
    }
    return null;
  }

  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    String authorizationHeader = request.getHeader("Authorization");
    if (Objects.nonNull(authorizationHeader)
        && authorizationHeader.startsWith("Bearer ")) {
          String token = authorizationHeader.substring(7);
          UsernamePasswordAuthenticationToken auth = getAuthentication(token);
          if(Objects.nonNull(auth)){
            SecurityContextHolder.getContext().setAuthentication(auth);
          }
    }
    filterChain.doFilter(request, response);
  }
}
