package com.salesianos.dam.journaltracker.service;

import com.salesianos.dam.journaltracker.dto.participante.EditParticipante;
import com.salesianos.dam.journaltracker.dto.participante.GetParticipanteList;
import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.model.Participante;
import com.salesianos.dam.journaltracker.repository.ParticpanteRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.ExtendedSelfDirtinessTracker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipanteService {

    //service incompleto(básico) pues el enunciado lo define así

    private final ParticpanteRepository particpanteRepository;


    public List<Participante> getAllParticipantes (){
        List<Participante> result = particpanteRepository.findAll();
        return result;
    }


    public Participante getParticipanteById (Long id){
        return particpanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Particpante no encontrado con id: " + id));
        //cambiar la excepción general por creada propia

    }

    public Participante addParticipante (EditParticipante editParticipante){
        Participante p = editParticipante.to();
        return particpanteRepository.save(p);
    }
}
