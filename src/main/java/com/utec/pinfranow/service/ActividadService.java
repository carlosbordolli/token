package com.utec.pinfranow.service;

import com.utec.pinfranow.model.Actividad;
import com.utec.pinfranow.model.Estado;
import com.utec.pinfranow.repository.ActividadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActividadService {

    private final ActividadRepository actividadRepository;

    public Actividad save(Actividad actividad) {
        return actividadRepository.save(actividad);
    }
    public List<Actividad> findAll() {
        return actividadRepository.findAll();
    }

    public Optional<Actividad> findById(Integer id) {
        return actividadRepository.findById(id);
    }

    public Actividad guardarActividad(Actividad actividad) {
        validarActividad(actividad);
        return actividadRepository.save(actividad);
    }

    public Actividad actualizarActividad(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    public boolean deleteById(Integer id) {
        if (actividadRepository.existsById(id)) {
            actividadRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(Integer id) {
        return actividadRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre) {
        return actividadRepository.existsByNombre(nombre);
    }



    public void validarActividad(Actividad actividad){

        // Validar si hay solapamiento
        boolean haySolapamiento = !actividadRepository.findByEspacioIdAndFechaAndHora(
                actividad.getEspacio().getId(), actividad.getFecha(), actividad.getHora()).isEmpty();
        if (haySolapamiento) {
            throw new IllegalArgumentException("Ya existe una actividad en el mismo espacio, fecha y hora.");
        }

        //validar que el registro de una actividad no sea con fecha anterior al día de hoy
        if (actividad.getFecha().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de la actividad no puede ser anterior al día actual.");
        }

    //no permitir que la duración de una actividad sea menor a 5 minutos por lo menos
        if (actividad.getDuracion() == null || actividad.getDuracion().isBefore(LocalTime.of(0, 5))) {
            throw new IllegalArgumentException("La duración mínima de una actividad debe ser de al menos 5 minutos.");
        }

    }

    public List<Actividad> findActividadByInscripcionFiltrada(
            String nombre,
            String tipoActividad,
            LocalDate fecha,
            Integer costo,
            boolean activos
    ) {
        return actividadRepository.findAll().stream()
                .filter(a -> Boolean.TRUE.equals(a.getInscripcion())) // Solo actividades con inscripción
                .filter(a -> activos ? a.getEstado() == Estado.ACTIVO : true)
                .filter(a -> nombre == null || a.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(a -> tipoActividad == null || a.getTipoActividad().getNombre().equalsIgnoreCase(tipoActividad))
                .filter(a -> fecha == null || fecha.equals(a.getFecha()))
                .filter(a -> costo == null || a.getCosto().compareTo(costo) == 0)
                .collect(Collectors.toList());
    }

    public Actividad cancelarActividad(Integer id){
        Actividad actividad = findById(id).orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada"));
        actividad.setEstado(Estado.INACTIVO);
        actualizarActividad(actividad);
        return actividad;
    }

}
