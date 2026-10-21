package com.salesianos.dam.journaltracker.dto.organizador;

import com.salesianos.dam.journaltracker.model.Organizador;

public record GetOrganizadorList(
        Long id,
        String nombre,
        String email

) {

    public static GetOrganizadorList of (Organizador o){
        return new GetOrganizadorList(
                o.getId(),
                o.getNombre(),
                o.getEmail()
        );
    }

}
