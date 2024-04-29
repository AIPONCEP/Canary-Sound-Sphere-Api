package com.api.canarysoundsphereapi.security.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.api.canarysoundsphereapi.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
  // Se obtienen las claves secreta y el tiempo de expiración del token desde el
  // archivo de propiedades
  @Value("${canarysoundsphereapi.app.jwtSecret}")
  private String jwtSecret;

  @Value("${canarysoundsphereapi.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  // Método para generar un token JWT a partir de la autenticación del usuario
  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject((userPrincipal.getUsername())) // Establece el nombre de usuario como el "subject" del token
        .setIssuedAt(new Date()) // Establece la fecha de emisión del token como la fecha actual
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Establece la fecha de expiración del token
        .signWith(key(), SignatureAlgorithm.HS256) // Firma el token con el algoritmo HMAC y la clave secreta
        .compact();
  }

  // Método privado para obtener la clave secreta en formato adecuado
  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  // Método para obtener el nombre de usuario a partir de un token JWT
  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build() // Configura el parser para utilizar la clave secreta
        .parseClaimsJws(token).getBody().getSubject(); // Extrae el sujeto (nombre de usuario) del cuerpo del token
  }

  // Método para validar un token JWT
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken); // Intenta parsear el token
      return true; // Si no hay excepciones, el token es válido
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false; // Si se atrapa alguna excepción, el token no es válido
  }
}