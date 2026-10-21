package com.salesianos.dam.journaltracker.error;

import com.salesianos.dam.journaltracker.error.notfound.OrganizadorNotFoundException;
import com.salesianos.dam.journaltracker.error.notfound.TallerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
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


    @ExceptionHandler(TallerYaExisteException.class)
    public ProblemDetail handleTallerExistente(TallerYaExisteException ex){

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND); //chequear .NOT_FOUND
        problem.setTitle("Taller ya existe");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/taller-already-exists"));

        return problem;
    }
}
