package com.salesianos.dam.journaltracker.service;

import com.salesianos.dam.journaltracker.dto.taller.EditTallerDto;
import com.salesianos.dam.journaltracker.error.ParticipanteYaInscritoException;
import com.salesianos.dam.journaltracker.error.TallerYaExisteException;
import com.salesianos.dam.journaltracker.error.notfound.TallerNotFoundException;
import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.model.Participante;
import com.salesianos.dam.journaltracker.model.Taller;
import com.salesianos.dam.journaltracker.repository.ParticipanteRepository;
import com.salesianos.dam.journaltracker.repository.TallerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
//import com.salesianos.dam.journaltracker.error.notfound.ParticipanteNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TallerService {

    private final TallerRepository tallerRepository;
    private final ParticipanteRepository participanteRepository;

    public List<Taller> getAllTalleres (){
        List<Taller> result = tallerRepository.findAll();
        if (result.isEmpty()) {
            throw new TallerNotFoundException();
        }
        return result;
    }

    public Taller getTallerById (Long id){
        return tallerRepository.findById(id)
                .orElseThrow(() -> new TallerNotFoundException(id));
    }

    public List<Taller> getTallerbyOrganizador(Long id){
        List<Taller> result = tallerRepository.findByOrganizadorId(id);
        if (result.isEmpty()) {
            throw new TallerNotFoundException("Taller not found with organizador id " + id);
        }
        return result;
    }

    public List<Taller> getTallerbyParticipante(Long id){
        List<Taller> result = tallerRepository.findByParticipantesId(id);
        if (result.isEmpty()) {
            throw new TallerNotFoundException("Taller not found with participante id " + id);
        }
        return result;
    }

    public Taller addTaller (EditTallerDto editTallerDto){
        //add validation w/ 1annotations or 2method (1>2)
        //Taller t = editTallerDto.to();
        // cuidado con relaciones, implementar organizador y particpante (same as model class)

        String titulo = editTallerDto.titulo().trim();
        if (tallerRepository.existsByTituloIgnoreCase(titulo)) {
            throw new TallerYaExisteException("Ya existe un taller con el título '" + titulo + "'");
        }
        Taller t = editTallerDto.to(); //revisar: para usar el trimmed, se debería usar titulo en lugar de editTallerDto.titulo() ?
        return tallerRepository.save(t);
    }

    public Taller updateTaller (Long id, EditTallerDto editTallerDto) {
        String titulo = editTallerDto.titulo().trim();
        if (tallerRepository.existsByTituloIgnoreCaseAndIdNot(titulo, id)) {
            throw new TallerYaExisteException("Ya existe un taller con el título '" + titulo + "'");
        }

        return tallerRepository.findById(id)
                .map(t -> {
                    t.setTitulo(titulo); //cambiado para usar el trimmed en lugar del default del dto
                    t.setFechaHoraInicio(editTallerDto.fechaHoraInicio());
                    t.setFechaHoraFin(editTallerDto.fechaHoraFin());
                    t.setOrganizador(editTallerDto.organizador());
                    t.setParticipantes(editTallerDto.participantes());
                    return tallerRepository.save(t);
                }).orElseThrow(() -> new TallerNotFoundException(id));
    }

    public void deleteTallerById (Long id){
        tallerRepository.deleteById(id);
    }

    /*public void deleteTaller (Taller taller){
        tallerRepository.delete(taller);
    }*/


    public Organizador getOrganizadorDeTaller(Long tallerId) {
        return tallerRepository.findById(tallerId)
                .orElseThrow(() -> new RuntimeException("Taller no encontrado"))
                .getOrganizador();
    }


    //para implementar la excepción propia del taller ya existente
    public Taller inscribirParticipante(Long tallerId, Long participanteId) {
        Taller t = tallerRepository.findById(tallerId).orElseThrow(() -> new TallerNotFoundException(tallerId));
        Participante p = participanteRepository.findById(participanteId).orElseThrow(() -> new RuntimeException("Error, participante con el id del taller proporcionado no encontrado"));
        //new ParticipanteNotFoundException(participanteId));

        if (t.getParticipantes().contains(p)) {
            throw new ParticipanteYaInscritoException("El participante " + participanteId + " ya está inscrito en el taller " + tallerId);
        }

        t.getParticipantes().add(p);
        return tallerRepository.save(t);
    }

    public List<Participante> getParticipantesDeTaller(Long tallerId) {
        Taller t = tallerRepository.findById(tallerId).orElseThrow(() -> new TallerNotFoundException(tallerId));
        return t.getParticipantes();
    }

    public void eliminarInscripcion(Long tallerId, Long participanteId) {
        Taller t = tallerRepository.findById(tallerId).orElseThrow(() -> new TallerNotFoundException(tallerId));
        Participante p = participanteRepository.findById(participanteId).orElseThrow(() -> new RuntimeException("Error, participante con el id del taller proporcionado no encontrado"));
        //new ParticipanteNotFoundException(participanteId));

        t.getParticipantes().remove(p);
        tallerRepository.save(t);
    }
}
