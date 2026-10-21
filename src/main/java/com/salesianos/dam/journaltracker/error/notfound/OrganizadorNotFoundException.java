package com.salesianos.dam.journaltracker.error.notfound;

public class OrganizadorNotFoundException extends RuntimeException {

    public OrganizadorNotFoundException() {
        super("Organizador no encontrado");
    }

    public OrganizadorNotFoundException(Long id) {
        super("Organizador no encontrado con id "+id);
    }

    public OrganizadorNotFoundException(String message) {
        super(message);
    }
}
