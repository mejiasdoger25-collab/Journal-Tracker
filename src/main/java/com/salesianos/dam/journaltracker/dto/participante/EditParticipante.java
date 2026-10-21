package com.salesianos.dam.journaltracker.dto.participante;

import com.salesianos.dam.journaltracker.model.Participante;
import com.salesianos.dam.journaltracker.model.Taller;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record EditParticipante(
        @NotBlank(message = "{editParticipante.nombreCompleto.notBlank}")
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
