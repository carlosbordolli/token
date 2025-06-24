package com.utec.pinfranow.controller;

import com.utec.pinfranow.dto.AuthRequest;
import com.utec.pinfranow.dto.AuthResponse;
import com.utec.pinfranow.jwt.JwtTokenUtil;
import com.utec.pinfranow.service.CustomUsuarioDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUsuarioDetailsService customUsuarioDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authenticationManager, CustomUsuarioDetailsService customUsuarioDetailsService, JwtTokenUtil jwtTokenUtil){
        this.authenticationManager = authenticationManager;
        this.customUsuarioDetailsService = customUsuarioDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception{

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (Exception e) {
            throw new Exception("Nombre de usuario o password ingresada es incorrecto el dato!", e);
        }

        final UserDetails userDetails = customUsuarioDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwt));


    }
}

