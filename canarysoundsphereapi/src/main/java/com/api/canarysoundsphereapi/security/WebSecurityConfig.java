package com.api.canarysoundsphereapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.api.canarysoundsphereapi.security.jwt.AuthEntryPointJwt;
import com.api.canarysoundsphereapi.security.jwt.AuthTokenFilter;
import com.api.canarysoundsphereapi.security.services.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
  // Servicio personalizado para obtener detalles del usuario
  @Autowired
  UserDetailsServiceImpl userDetailsService;
  // Controlador para manejar las solicitudes no autorizadas
  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  // Configuración del filtro de autenticación basado en JWT
  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  // Proveedor de autenticación DAO para autenticar usuarios y cifrar contraseñas
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  // Gestor de autenticación para gestionar procesos de autenticación
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  // Codificador de contraseñas para cifrar y descifrar contraseñas
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Configuración de la cadena de filtros de seguridad para las solicitudes HTTP
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()) // Deshabilita la protección CSRF
        // Maneja las excepciones de autenticación
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        // Configura la gestión de sesiones
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth // Permite el acceso a rutas de autenticación

            .requestMatchers(HttpMethod.POST, "/events").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/events/{id}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/events/{id}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/events").permitAll()
            .requestMatchers(HttpMethod.GET, "/events/{id}").permitAll()

            .requestMatchers(HttpMethod.POST, "/authors").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/authors/{id}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/authors/{id}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/authors").permitAll()
            .requestMatchers(HttpMethod.GET, "/authors/{id}").permitAll()

            .requestMatchers("/api-doc/**").permitAll()

            .requestMatchers("/api/auth/**").permitAll()

            .anyRequest().authenticated()); // Requiere autenticación para cualquier otra solicitud

    http.authenticationProvider(authenticationProvider()); // Configura el proveedor de autenticación

    // Agrega el filtro de tokens JWT antes del filtro de usuario y contraseña
    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build(); // Devuelve la cadena de filtros de seguridad configurada
  }
}