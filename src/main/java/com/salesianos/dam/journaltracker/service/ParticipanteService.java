package com.salesianos.dam.journaltracker.service;

import com.salesianos.dam.journaltracker.dto.participante.EditParticipante;
import com.salesianos.dam.journaltracker.model.Participante;
import com.salesianos.dam.journaltracker.repository.ParticipanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipanteService {

    //service incompleto(básico) pues el enunciado lo define así

    private final ParticipanteRepository participanteRepository;


    public List<Participante> getAllParticipantes (){
        List<Participante> result = participanteRepository.findAll();
        return result;
    }


    public Participante getParticipanteById (Long id){
        return participanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Particpante no encontrado con id: " + id));
        //cambiar la excepción general por creada propia

    }

    public Participante addParticipante (EditParticipante editParticipante){
        Participante p = editParticipante.to();
        return participanteRepository.save(p);
    }
}
