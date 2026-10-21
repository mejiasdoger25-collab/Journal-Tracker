package com.salesianos.dam.journaltracker.controller;

import com.salesianos.dam.journaltracker.dto.participante.EditParticipante;
import com.salesianos.dam.journaltracker.dto.participante.GetParticipanteList;
import com.salesianos.dam.journaltracker.model.Participante;
import com.salesianos.dam.journaltracker.repository.ParticipanteRepository;
import com.salesianos.dam.journaltracker.service.ParticipanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/participante")
public class ParticipanteController {

    //controller incompleto(básico) pues el enunciado lo define así

    private final ParticipanteRepository participanteRepository;
    private final ParticipanteService participanteService;

    @GetMapping
    public ResponseEntity<List<GetParticipanteList>> getAllParticipantes(){
        List<Participante> result = participanteService.getAllParticipantes();
        return  ResponseEntity.ok(
                result
                        .stream()
                        .map(GetParticipanteList::of)
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<GetParticipanteList> addPartticipante (@RequestBody EditParticipante editParticipante){
        return ResponseEntity.status(201).body(
                GetParticipanteList.of(participanteService.addParticipante(editParticipante))
        );
    }

}
