package com.salesianos.dam.journaltracker.dto.taller;

import com.salesianos.dam.journaltracker.model.Taller;

import java.time.LocalDateTime;

public record GetTallerList(
        Long id,
        String titulo,
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin
) {
    public static GetTallerList of (Taller t){
        return new GetTallerList(
                t.getId(),
                t.getTitulo(),
                t.getFechaHoraInicio(),
                t.getFechaHoraFin()
        );
    }
}
