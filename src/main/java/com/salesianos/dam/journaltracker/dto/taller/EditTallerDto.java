package com.salesianos.dam.journaltracker.dto.taller;

import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.model.Participante;
import com.salesianos.dam.journaltracker.model.Taller;

import java.time.LocalDateTime;
import java.util.List;

public record EditTallerDto(
        String titulo,
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin,
        Organizador organizador,
        List<Participante> participantes
) {
    public Taller to(){
        return Taller.builder()
                .titulo(titulo)
                .fechaHoraInicio(fechaHoraInicio)
                .fechaHoraFin(fechaHoraFin)
                .organizador(organizador)
                .participantes(participantes)
                .build();
    }
}
