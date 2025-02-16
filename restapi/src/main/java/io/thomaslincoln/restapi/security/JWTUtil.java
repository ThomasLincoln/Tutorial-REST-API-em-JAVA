package io.thomaslincoln.restapi.security;

import java.util.Date;
import java.util.Objects;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {
  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration}")
  private Long expiration;

  private SecretKey getKeyBySecret() {
    SecretKey key = Keys.hmacShaKeyFor(this.secret.getBytes());
    return key;
  }

  public String generateToken(String username) {
    SecretKey key = getKeyBySecret();
    return Jwts.builder()
        .subject(username)
        .expiration(new Date(System.currentTimeMillis() + this.expiration))
        .signWith(key)
        .compact();
  }

  public boolean isValidToken(String token) {
    Claims claims = getClaims(token);
    if (Objects.nonNull(claims)) {
      String username = claims.getSubject();
      Date expirationDate = claims.getExpiration();
      Date now = new Date(System.currentTimeMillis());
      if (Objects.nonNull(username) && Objects.nonNull(expirationDate) && now.before(expirationDate)) {
        return true;
      }
    }
    return false;
  }

  private Claims getClaims(String token) {
    SecretKey key = getKeyBySecret();
    try {
      return Jwts.parser()
          .verifyWith(key)
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (Exception e) {
      return null;
    }
  }

  public String getUsername(String token){
    Claims claims = getClaims(token);
    if(Objects.nonNull(claims)){
      return claims.getSubject();
    }
    return null;
  }
}
