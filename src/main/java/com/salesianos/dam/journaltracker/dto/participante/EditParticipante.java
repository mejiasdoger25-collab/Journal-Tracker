package com.salesianos.dam.journaltracker.dto.participante;

import com.salesianos.dam.journaltracker.model.Participante;
import com.salesianos.dam.journaltracker.model.Taller;

import java.util.List;

public record EditParticipante(
        String nombreCompleto,
        List<Taller> taller
) {
    public Participante to() {
        return Participante.builder()
                .nombreCompleto(nombreCompleto)
                .taller(taller)
                .build();
    }
}
