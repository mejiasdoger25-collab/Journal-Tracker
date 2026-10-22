package com.salesianos.dam.journaltracker.validation;

import com.salesianos.dam.journaltracker.dto.taller.EditTallerDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FechaFinPosteriorValidator
        implements ConstraintValidator<FechaFinPosterior, EditTallerDto> {

    @Override
    public boolean isValid(EditTallerDto dto, ConstraintValidatorContext context) {
        if (dto.fechaHoraInicio() == null || dto.fechaHoraFin() == null) {
            return true; //la anotación @not null se encarga
        }
        return dto.fechaHoraFin().isAfter(dto.fechaHoraInicio());
    }
}