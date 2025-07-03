package com.utec.pinfranow.security;

import com.utec.pinfranow.jwt.JwtRequestFilter;
import com.utec.pinfranow.service.CustomUsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUsuarioDetailsService customUsuarioDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfig(CustomUsuarioDetailsService customUsuarioDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUsuarioDetailsService = customUsuarioDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/api/auth/**",
                                "/actuator/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/login.html"
                        ).permitAll()

                        // FUNCIONALIDAD - Solo ADMIN
                        .requestMatchers("/api/funcionalidades/**").hasRole("ADMIN")

                        // PAGOS
                        .requestMatchers(HttpMethod.GET, "/api/pagos/{id}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/pagos/{id}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/pagos/{id}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/pagos").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/pagos").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/pagos/usuario/{idUsuario}/fecha").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/pagos/fecha").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/pagos/actividad/{idActividad}").hasAnyRole("ADMIN", "AUXADMIN")

                        // INSCRIPCIONES
                        .requestMatchers(HttpMethod.POST, "/api/inscripciones").hasAnyRole("ADMIN", "AUXADMIN", "SOCIO", "NO_SOCIO")
                        .requestMatchers(HttpMethod.GET, "/api/inscripciones/{idUsuario}/{idActividad}").hasAnyRole("ADMIN", "AUXADMIN", "SOCIO", "NO_SOCIO")
                        .requestMatchers(HttpMethod.DELETE, "/api/inscripciones/{idUsuario}/{idActividad}").hasAnyRole("ADMIN", "AUXADMIN", "SOCIO", "NO_SOCIO")
                        .requestMatchers(HttpMethod.GET, "/api/inscripciones").hasAnyRole("ADMIN", "AUXADMIN")

                        // AUXILIARES
                        .requestMatchers(HttpMethod.GET, "/api/auxiliares").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/auxiliares").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/auxiliares/{id}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/auxiliares/{id}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/auxiliares/usuario/{idUsuario}").hasAnyRole("ADMIN", "AUXADMIN")

                        // PERFILES
                        .requestMatchers(HttpMethod.GET, "/api/perfiles").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/perfiles").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/perfiles/{id}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/perfiles/{id}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/perfiles/{id}").hasAnyRole("ADMIN", "AUXADMIN")

                        // USUARIOS
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/usuarios").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/{id}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/{id}").hasAnyRole("ADMIN", "AUXADMIN")

                        // ESPACIOS
                        .requestMatchers(HttpMethod.GET, "/api/espacios").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/espacios").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/espacios/{id}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/espacios/{id}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/espacios/{id}").hasAnyRole("ADMIN", "AUXADMIN")

                        // ACTIVIDADES
                        .requestMatchers(HttpMethod.GET, "/api/actividades").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/actividades").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/actividades/{id}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/actividades/{id}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/actividades/{id}").hasAnyRole("ADMIN", "AUXADMIN")

                        // ACCESOS A FUNCIONALIDADES
                        .requestMatchers(HttpMethod.GET, "/api/accesos-funcionalidades").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/accesos-funcionalidades").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/accesos-funcionalidades/perfil/{idPerfil}").hasAnyRole("ADMIN", "AUXADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/accesos-funcionalidades/{idPerfil}/{idFuncionalidad}").hasAnyRole("ADMIN", "AUXADMIN")

                        // Default: cualquier otra requiere autenticación
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler)
                );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder
                .userDetailsService(customUsuarioDetailsService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

