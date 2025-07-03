package com.utec.pinfranow.service;

import com.utec.pinfranow.dto.FiltroReporteActividadDTO;
import com.utec.pinfranow.dto.FiltroReporteTipoActividadDTO;
import com.utec.pinfranow.model.Actividad;
import com.utec.pinfranow.model.ids.InscripcionActividadId;
import com.utec.pinfranow.model.InscripcionActividad;
import com.utec.pinfranow.repository.InscripcionActividadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InscripcionActividadService {

    private final InscripcionActividadRepository inscripcionActividadRepository;

    public List<InscripcionActividad> findAll() {
        return inscripcionActividadRepository.findAll();
    }

    public Optional<InscripcionActividad> findById(InscripcionActividadId id) {
        return inscripcionActividadRepository.findById(id);
    }

    public InscripcionActividad save(InscripcionActividad inscripcionActividad) {
        Actividad actividad = inscripcionActividad.getActividad();

        if (actividad.getInscripcion() == null || !actividad.getInscripcion()) {
            throw new IllegalStateException("La actividad no necesita inscripción");
        }
        LocalDate hoy = LocalDate.now();
        if (actividad.getFecAperturaInscripcion() != null && actividad.getFecCierreInscripcion() != null) {
            if (hoy.isBefore(actividad.getFecAperturaInscripcion()) || hoy.isAfter(actividad.getFecCierreInscripcion())) {
                throw new IllegalStateException("El período de inscripción está cerrado para esta actividad");
            }
        }

        return inscripcionActividadRepository.save(inscripcionActividad);
    }

    public boolean cancelarInscripcion(InscripcionActividadId id) {
        Optional<InscripcionActividad> inscripcionPorId = inscripcionActividadRepository.findById(id);

        if (inscripcionPorId.isPresent()) {
            InscripcionActividad inscripcion = inscripcionPorId.get();
            inscripcion.setCancelada(true);
            inscripcionActividadRepository.save(inscripcion);
            return true;
        }

        return false;
    }

    public boolean deleteById(InscripcionActividadId id) {
        if (inscripcionActividadRepository.existsById(id)) {
            inscripcionActividadRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(InscripcionActividadId id) {
        return inscripcionActividadRepository.existsById(id);
    }

    public List<InscripcionActividad> obtenerReportePorActividades(FiltroReporteActividadDTO filtro) {
        LocalDateTime desde = filtro.getFechaDesde() != null ? filtro.getFechaDesde().atStartOfDay() : LocalDateTime.MIN;

        LocalDateTime hasta = filtro.getFechaHasta() != null ? filtro.getFechaHasta().atTime(23, 59, 59) : LocalDateTime.now();

        String movimiento = filtro.getTipoMovimiento();
        final String tipoMovimiento = (movimiento == null || movimiento.isBlank()
                || (!movimiento.equalsIgnoreCase("INSCRIPCION") && !movimiento.equalsIgnoreCase("CANCELACION")))
                ? "AMBAS"
                : movimiento.toUpperCase();

        return inscripcionActividadRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.greaterThanOrEqualTo(root.get("fechaInscripcion"), desde));
            predicates.add(cb.lessThanOrEqualTo(root.get("fechaInscripcion"), hasta));

            if (filtro.getIdActividades() != null &&
                    !filtro.getIdActividades().isEmpty() &&
                    !filtro.getIdActividades().contains(0)) {
                predicates.add(root.get("actividad").get("id").in(filtro.getIdActividades()));
            }

            if (!"AMBAS".equalsIgnoreCase(tipoMovimiento)) {
                boolean cancelada = "CANCELACION".equalsIgnoreCase(tipoMovimiento);
                predicates.add(cb.equal(root.get("cancelada"), cancelada));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
    public List<InscripcionActividad> obtenerReportePorTipoActividad(FiltroReporteTipoActividadDTO filtro) {
        LocalDateTime desde = (filtro.getFechaDesde() != null) ? filtro.getFechaDesde().atStartOfDay() : LocalDateTime.MIN;

        LocalDateTime hasta = (filtro.getFechaHasta() != null) ? filtro.getFechaHasta().atTime(23, 59, 59) : LocalDateTime.now();

        String movimiento = filtro.getTipoMovimiento();
        final String tipoMovimiento = (movimiento == null || movimiento.isBlank() || (!movimiento.equalsIgnoreCase("INSCRIPCION") && !movimiento.equalsIgnoreCase("CANCELACION"))) ? "AMBAS" : movimiento.toUpperCase();

        return inscripcionActividadRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            //por fechas
            predicates.add(cb.greaterThanOrEqualTo(root.get("fechaInscripcion"), desde));
            predicates.add(cb.lessThanOrEqualTo(root.get("fechaInscripcion"), hasta));

            //por tipo de actividad
            if (filtro.getIdTiposActividad() != null && !filtro.getIdTiposActividad().isEmpty()) {
                predicates.add(root.get("actividad").get("tipoActividad").get("id")
                        .in(filtro.getIdTiposActividad()));
            }

            //por movimiento
            if (!"AMBAS".equalsIgnoreCase(tipoMovimiento)) {
                boolean cancelada = "CANCELACION".equalsIgnoreCase(tipoMovimiento);
                predicates.add(cb.equal(root.get("cancelada"), cancelada));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }





}
