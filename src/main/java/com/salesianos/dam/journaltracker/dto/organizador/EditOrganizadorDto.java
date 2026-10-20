package com.salesianos.dam.journaltracker.dto.organizador;

import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.model.Taller;

import java.util.List;

public record EditOrganizadorDto(
        String nombre,
        String email,
        List<Taller> talleres
) {

    public Organizador to(){
        return Organizador.builder()
                .nombre(nombre)
                .email(email)
                .talleres(talleres)
                .build();
    }

}
