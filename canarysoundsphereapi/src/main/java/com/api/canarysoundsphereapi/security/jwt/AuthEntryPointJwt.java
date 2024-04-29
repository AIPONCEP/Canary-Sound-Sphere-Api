package com.api.canarysoundsphereapi.security.jwt;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
	// Logger estático para registrar mensajes relacionados con la autenticación
	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
	// Método de la interfaz AuthenticationEntryPoint que maneja las solicitudes no autenticadas
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// Registro de un mensaje de error utilizando el logger
		logger.error("Unauthorized error: {}", authException.getMessage());
		// Envío de una respuesta de error al cliente con el código de estado HTTP 401 (Unauthorized)
        // Se incluye un mensaje de error simple en el cuerpo de la respuesta
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
	}

}