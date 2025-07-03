package com.utec.pinfranow.service;

import com.utec.pinfranow.dto.TelefonoDTO;
import com.utec.pinfranow.model.Telefono;
import com.utec.pinfranow.model.Usuario;
import com.utec.pinfranow.model.ids.TelefonoId;
import com.utec.pinfranow.repository.TelefonoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
    @RequiredArgsConstructor
    public class TelefonoService {

        private final TelefonoRepository telefonoRepository;

        public List<Telefono> guardarTelefonos(List<String> numeros, Usuario usuario) {
            List<Telefono> telefonos = numeros.stream()
                    .map(numero -> Telefono.builder()
                            .id(new TelefonoId(numero, usuario))
                            .build())
                    .collect(Collectors.toList());

            return telefonoRepository.saveAll(telefonos);
        }

        public Optional<Telefono> buscarPorId(String numero, Usuario usuario) {
            TelefonoId id = new TelefonoId(numero, usuario);
            return telefonoRepository.findById(id);
        }


        public void eliminarTelefono(String numero, Usuario usuario) {
            TelefonoId id = new TelefonoId(numero, usuario);
            telefonoRepository.deleteById(id);
        }
}
