package com.salesianos.dam.journaltracker.error;

import com.salesianos.dam.journaltracker.error.notfound.OrganizadorNotFoundException;
import com.salesianos.dam.journaltracker.error.notfound.ParticipanteNotFoundException;
import com.salesianos.dam.journaltracker.error.notfound.TallerNotFoundException;
//import jakarta.persistence.EntityNotFoundException;
import com.salesianos.dam.journaltracker.error.notfound.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Recurso no encontrado");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/not-found"));

        return problem;
    }

    @ExceptionHandler(OrganizadorNotFoundException.class)
    public ProblemDetail handleOrganizadorNotFound(OrganizadorNotFoundException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Organizador no encontrado");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/organizador-not-found"));

        return problem;
    }

    @ExceptionHandler(TallerNotFoundException.class)
    public ProblemDetail handleTallerNotFound(TallerNotFoundException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Taller no encontrado");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/taller-not-found"));

        return problem;
    }

    @ExceptionHandler(ParticipanteNotFoundException.class)
    public ProblemDetail handleParticipanteNotFound(ParticipanteNotFoundException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Participante no encontrado");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/participante-not-found"));

        return problem;
    }


    @ExceptionHandler(TallerYaExisteException.class)
    public ProblemDetail handleTallerExistente(TallerYaExisteException ex){

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT); //409, no 404: es 9 porque el 404 es para cuando falta el recurso, además, la petición no está mal formada. Es un conflicto con el server pues ya existe con ese name, por eso el uso de 409 instead of 404
        problem.setTitle("Taller ya existe");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/taller-already-exists"));

        return problem;
    }

    @ExceptionHandler(ParticipanteYaInscritoException.class)
    public ProblemDetail handleParticipanteYaInscrito(ParticipanteYaInscritoException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Participante ya inscrito");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/participante-ya-inscrito"));
        return problem;
    }
}
