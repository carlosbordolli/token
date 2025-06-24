package com.utec.pinfranow.configuration;

import com.utec.pinfranow.service.RegistroActividadService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuditoriaFiltro extends OncePerRequestFilter {

    private final RegistroActividadService registroActividadService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String metodo = request.getMethod();
        String endpoint = request.getRequestURI();

        //Rutas las cuales no vamos a auditar para no ensuciar el registro ya que Swaggwer ejecuta internamente
        if (endpoint.startsWith("/swagger-ui")
                || endpoint.startsWith("/v3/api-docs")
                || endpoint.startsWith("/login")
                || endpoint.startsWith("/actuator")
                || endpoint.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String usuario = "ANÓNIMO";
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                usuario = userDetails.getUsername();
            } else {
                usuario = principal.toString();
            }
        }

        registroActividadService.registrar(usuario, metodo, endpoint);

        filterChain.doFilter(request, response);
    }
}
