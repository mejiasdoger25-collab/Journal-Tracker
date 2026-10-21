package com.salesianos.dam.journaltracker.service;

import com.salesianos.dam.journaltracker.dto.participante.EditParticipante;
import com.salesianos.dam.journaltracker.error.notfound.ParticipanteNotFoundException;
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
                .orElseThrow(() -> new ParticipanteNotFoundException(id));
    }

    public Participante addParticipante (EditParticipante editParticipante){
        Participante p = editParticipante.to();
        return participanteRepository.save(p);
    }
}
