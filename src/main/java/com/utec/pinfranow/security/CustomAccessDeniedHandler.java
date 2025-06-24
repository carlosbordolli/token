package com.utec.pinfranow.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utec.pinfranow.service.RegistroActividadService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final RegistroActividadService registroActividadService;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        // Registrar intento fallido
        String endpoint = request.getRequestURI();
        String metodo = request.getMethod();
        String usuario = "ANONIMO";

        var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            usuario = auth.getName();
        }

        registroActividadService.registrar(usuario + " (ACCESO DENEGADO)", metodo, endpoint);

        // Enviar respuesta 403 personalizada
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        Map<String, Object> body = new HashMap<>();
        body.put("status", 403);
        body.put("error", "Acceso prohibido");
        body.put("message", "Acceso denegado");
        body.put("path", endpoint);

        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
}
