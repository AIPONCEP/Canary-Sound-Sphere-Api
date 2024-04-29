package com.api.canarysoundsphereapi.security.jwt;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.api.canarysoundsphereapi.security.services.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
  // Inyección de dependencias para utilizar JwtUtils y UserDetailsServiceImpl
  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;
  // Logger estático para registrar mensajes relacionados con el filtro de token de autenticación
  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
  
  // Método principal para filtrar cada solicitud entrante
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
        String jwt = parseJwt(request); // Extraer el token JWT de la solicitud entrante

        if (jwt != null && jwtUtils.validateJwtToken(jwt)) { // Validar el token JWT y procesarlo si es válido
          
          String username = jwtUtils.getUserNameFromJwtToken(jwt); // Obtener el nombre de usuario del token JWT
          
          // Cargar los detalles del usuario utilizando el servicio de detalles de usuario
          UserDetails userDetails = userDetailsService.loadUserByUsername(username); 
          
          // Crear una instancia de autenticación basada en el nombre de usuario y los detalles del usuario
          UsernamePasswordAuthenticationToken authentication = 
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          // Establecer los detalles de autenticación en el contexto de seguridad de Spring
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          // Establecer la autenticación en el contexto de seguridad
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    } catch (Exception e) {
          logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response); // Continuar con la cadena de filtros

  }

  // Método para extraer el token JWT de la solicitud
  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    // Si el encabezado Authorization contiene un token JWT, lo devuelve
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }
    // Si no se encuentra ningún token JWT en el encabezado Authorization, devuelve null
    return null;
  }
}