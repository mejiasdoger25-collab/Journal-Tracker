package com.salesianos.dam.journaltracker.dto.organizador;

import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.model.Taller;

import java.util.List;

public record GetOrganizadorDetail(
        Long id,
        String nombre,
        String email,
        List<Taller> talleres
) {

    public static GetOrganizadorDetail of (Organizador o){
        return new GetOrganizadorDetail(
                o.getId(),
                o.getNombre(),
                o.getEmail(),
                o.getTalleres()
        );
    }

}
