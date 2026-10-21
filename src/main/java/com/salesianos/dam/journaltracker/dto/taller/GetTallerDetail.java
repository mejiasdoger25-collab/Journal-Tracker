package com.salesianos.dam.journaltracker.dto.taller;

import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.model.Participante;
import com.salesianos.dam.journaltracker.model.Taller;

import java.time.LocalDateTime;
import java.util.List;

public record GetTallerDetail(
        Long id,
        String titulo,
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin,
        Organizador organizador,
        List<Participante> participantes
) {
    public static GetTallerDetail of (Taller t){
        return new GetTallerDetail(
                t.getId(),
                t.getTitulo(),
                t.getFechaHoraInicio(),
                t.getFechaHoraFin(),
                t.getOrganizador(),
                t.getParticipantes()
        );
    }
}
