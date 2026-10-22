package com.salesianos.dam.journaltracker.dto.taller;

import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.model.Participante;
import com.salesianos.dam.journaltracker.model.Taller;
import com.salesianos.dam.journaltracker.validation.FechaFinPosterior;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@FechaFinPosterior(message = "{editTallerDto.FechaFinPosterior}")
public record EditTallerDto(
        @NotBlank(message = "{editTallerDto.titulo.notBlank}")
        String titulo,

        @NotNull(message = "{editTallerDto.fechaHoraInicio.notNull}")
        LocalDateTime fechaHoraInicio,

        @NotNull(message = "{editTallerDto.fechaHoraFin.notNull}")
        LocalDateTime fechaHoraFin,

        @NotNull(message = "{editTallerDto.organizador.notNull}")
        Long organizadorId,

        List<Participante> participantes
) {
    public Taller to() {
        return Taller.builder()
                .titulo(titulo)
                .fechaHoraInicio(fechaHoraInicio)
                .fechaHoraFin(fechaHoraFin)
                .participantes(participantes)
                .build();
    }
}
