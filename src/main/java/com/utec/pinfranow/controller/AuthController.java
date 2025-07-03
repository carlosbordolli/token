package com.utec.pinfranow.controller;

import com.utec.pinfranow.dto.AuthRequest;
import com.utec.pinfranow.dto.AuthResponse;
import com.utec.pinfranow.jwt.JwtTokenUtil;
import com.utec.pinfranow.model.Usuario;
import com.utec.pinfranow.service.CustomUsuarioDetailsService;
import com.utec.pinfranow.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUsuarioDetailsService customUsuarioDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager,
                          CustomUsuarioDetailsService customUsuarioDetailsService,
                          JwtTokenUtil jwtTokenUtil,
                          UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.customUsuarioDetailsService = customUsuarioDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new Exception("Nombre de usuario o contraseña incorrectos", e);
        }

        Usuario usuario = usuarioService.findByEmail(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String jwt = jwtTokenUtil.generateTokenFromUsuario(usuario);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}