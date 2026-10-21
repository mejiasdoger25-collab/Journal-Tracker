package com.salesianos.dam.journaltracker.error.notfound;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message) {
        super(message);
    }

}
