package com.salesianos.dam.journaltracker.service;

import com.salesianos.dam.journaltracker.dto.taller.EditTallerDto;
import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.model.Taller;
import com.salesianos.dam.journaltracker.repository.TallerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TallerService {

    private final TallerRepository tallerRepository;

    public List<Taller> getAllTalleres (){
        List<Taller> result = tallerRepository.findAll();
        /*if (result.isEmpty()) {
            throw new TallerNotFoundException();
        } para el siguiente bloque de excepciones*/
        return result;

        /*forma más limpia para no usar exception:return tallerRepository.findAll();*/
    }

    public Taller getTallerById (Long id){
        return tallerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taller no encontrado con id: "+ id));
    }

    public List<Taller> getTallerbyOrganizador(Long id){
        List<Taller> result = tallerRepository.findByOrganizadorId(id);
        return result;
        /*
        * if (result.isEmpty()) {
            throw new TallerNotFoundException("Taller not found with organizador id " + id);
        }
        * */
    }

    public List<Taller> getTallerbyParticipante(Long id){
        List<Taller> result = tallerRepository.findByParticipantesId(id);
        return result;
        /*
        * if (result.isEmpty()) {
            throw new TallerNotFoundException("Taller not found with participante id " + id);
        }
        * */
    }

    public Taller addTaller (EditTallerDto editTallerDto){
        //add validation w/ 1annotations or 2method (1>2)
        Taller t = editTallerDto.to();
        // cuidado con relaciones, implementar organizador y particpante (same as model class)
        return tallerRepository.save(t);
    }

    public Taller updateTaller (Long id, EditTallerDto editTallerDto) {
        return tallerRepository.findById(id)
                .map(t -> {
                    t.setTitulo(editTallerDto.titulo());
                    t.setFechaHoraInicio(editTallerDto.fechaHoraInicio());
                    t.setFechaHoraFin(editTallerDto.fechaHoraFin());
                    t.setOrganizador(editTallerDto.organizador());
                    t.setParticipantes(editTallerDto.participantes());
                    return tallerRepository.save(t);
                }).orElseThrow(RuntimeException::new);
        //.orElseThrow(() -> new TallerNotFoundException(id));
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

}
