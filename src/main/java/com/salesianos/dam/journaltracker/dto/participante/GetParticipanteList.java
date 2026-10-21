package com.salesianos.dam.journaltracker.dto.participante;

import com.salesianos.dam.journaltracker.model.Participante;
import com.salesianos.dam.journaltracker.model.Taller;

import java.util.List;

public record GetParticipanteList(
        Long id,
        String nombreCompleto,
        List<Taller> taller
) {

    public static GetParticipanteList of (Participante p){
        return new GetParticipanteList(
                p.getId(),
                p.getNombreCompleto(),
                p.getTaller()
        );
    }
}
