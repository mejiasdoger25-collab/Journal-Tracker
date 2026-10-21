package com.salesianos.dam.journaltracker.dto.organizador;

import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.model.Taller;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record EditOrganizadorDto(
        @NotBlank(message = "{editOrganizadorDto.nombre.notBlank}")
        String nombre,

        @NotBlank(message = "{editOrganizadorDto.email.notBlank}")
        @Email(message = "{editOrganizadorDto.email.email}")
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
