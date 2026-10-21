package com.salesianos.dam.journaltracker.error.notfound;

public class TallerNotFoundException extends RuntimeException {

    public TallerNotFoundException(){
        super("Taller no encontrado");
    }

    public TallerNotFoundException (Long id){
        super("Taller no encontrado con id "+ id);
    }

    public TallerNotFoundException(String message) {
        super(message);
    }
}
