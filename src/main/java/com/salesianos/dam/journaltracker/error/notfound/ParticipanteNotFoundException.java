package com.salesianos.dam.journaltracker.error.notfound;

public class ParticipanteNotFoundException extends EntityNotFoundException  {

  public ParticipanteNotFoundException() { super("Participante no encontrado"); }

  public ParticipanteNotFoundException(Long id) { super("Participante no encontrado con id " + id); }

  public ParticipanteNotFoundException(String message) {
        super(message);
    }
}
