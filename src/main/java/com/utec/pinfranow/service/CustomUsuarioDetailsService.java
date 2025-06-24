package com.utec.pinfranow.service;

import com.utec.pinfranow.model.UsuarioBackend;
import com.utec.pinfranow.repository.UsuarioBackendRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomUsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioBackendRepository usuarioBackendRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //Buscar el usuario en la base de datos, si no encontre ese usuario, lanzo una exepcion.
        UsuarioBackend user = usuarioBackendRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        //Construir un objeto UserDetails con los datos del usuario encontrado.
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("ADMIN".equals(user.getRole()) ? "ADMIN" : "USER")
                .build();
    }

    @PostConstruct
    public void initUsers(){
        //Verificar que no existan usuarios en la base de datos.
        if(usuarioBackendRepository.findAll().isEmpty()){

            //Codificador de contraseñas.
            PasswordEncoder enconder = new BCryptPasswordEncoder();

            //Usuario con el rol ADMIN.
            UsuarioBackend admin = new UsuarioBackend();
            admin.setUsername("admin");
            admin.setPassword(enconder.encode("admin123"));
            admin.setRole("ADMIN");
            usuarioBackendRepository.save(admin);

            //Usuario con el rol USER.
            UsuarioBackend user = new UsuarioBackend();
            user.setUsername("user");
            user.setPassword(enconder.encode("user123"));
            user.setRole("USER");
            usuarioBackendRepository.save(user);

        }
    }


}
