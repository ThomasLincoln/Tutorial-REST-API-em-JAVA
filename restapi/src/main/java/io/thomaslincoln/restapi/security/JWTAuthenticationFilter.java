package io.thomaslincoln.restapi.security;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.thomaslincoln.restapi.config.GlobalExceptionHandler;
import io.thomaslincoln.restapi.models.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

  private AuthenticationManager authenticationManager;
  private JWTUtil jwtUtil;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, 
    JWTUtil jwtUtil){
      setAuthenticationFailureHandler(new GlobalExceptionHandler());
      this.authenticationManager = authenticationManager;
      this.jwtUtil = jwtUtil;
    } 

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, 
  HttpServletResponse response) throws AuthenticationException{
    try {
      Usuario usuarioCredentials = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken
      (
        usuarioCredentials.getApelido(),
        usuarioCredentials.getSenha(),
        new ArrayList<>());
      Authentication authentication = this.authenticationManager
        .authenticate(token);
      return authentication;
    } catch (Exception e){
      throw new RuntimeException();
    }
  }

  protected void successfulAuthentication(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain,
    Authentication authentication
  ){
    UserSpringSecurity userSpringSecurity = (UserSpringSecurity) authentication.getPrincipal();
    String username = userSpringSecurity.getUsername();
    String token = jwtUtil.generateToken(username);
    response.addHeader("Authorization", "Bearer " + token);
    response.addHeader("acess-control-expose-headers", "Authorization");
  }
}
