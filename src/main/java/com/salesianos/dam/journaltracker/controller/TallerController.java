package com.salesianos.dam.journaltracker.controller;

import com.salesianos.dam.journaltracker.dto.organizador.GetOrganizadorDetail;
import com.salesianos.dam.journaltracker.dto.participante.GetParticipanteList;
import com.salesianos.dam.journaltracker.dto.taller.EditTallerDto;
import com.salesianos.dam.journaltracker.dto.taller.GetTallerDetail;
import com.salesianos.dam.journaltracker.dto.taller.GetTallerList;
import com.salesianos.dam.journaltracker.model.Participante;
import com.salesianos.dam.journaltracker.model.Taller;
import com.salesianos.dam.journaltracker.repository.TallerRepository;
import com.salesianos.dam.journaltracker.service.TallerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/taller")
public class TallerController {

    private final TallerRepository tallerRepository;
    private final TallerService tallerService;

    @GetMapping
    public ResponseEntity<List<GetTallerList>> getAllTalleres() {
        List<Taller> result = tallerService.getAllTalleres();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetTallerList::of)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTallerDetail> getTallerById(@PathVariable Long id) {
        return ResponseEntity.ok(GetTallerDetail.of(tallerService.getTallerById(id)));
    }


    @GetMapping("/organizador/{id}")
    public ResponseEntity<List<GetTallerList>> getTallerByOrganizador(@PathVariable Long id){
        List<Taller> result = tallerService.getTallerbyOrganizador(id);
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetTallerList::of)
                        .toList()
        );
    }

    @GetMapping("/{id}/organizador")
    public ResponseEntity<GetOrganizadorDetail> getOrganizadorDeTaller(@PathVariable Long id) {
        return ResponseEntity.ok(GetOrganizadorDetail.of(tallerService.getOrganizadorDeTaller(id)));
    }

    @GetMapping("/participante/{id}")
    public ResponseEntity<List<GetTallerList>> getTallerByParticipante(@PathVariable Long id){
        List<Taller> result = tallerService.getTallerbyParticipante(id);
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetTallerList::of)
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<GetTallerDetail> addTaller (@Valid @RequestBody EditTallerDto editTallerDto){
        return ResponseEntity.status(201).body(
                GetTallerDetail.of(tallerService.addTaller(editTallerDto))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetTallerDetail> updateTaller(@PathVariable Long id, @Valid @RequestBody EditTallerDto editTallerDto){
        return ResponseEntity.ok(
                GetTallerDetail.of(tallerService.updateTaller(id, editTallerDto))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaller(@PathVariable Long id) {
        tallerService.deleteTallerById(id);
        return ResponseEntity.noContent().build();
    }


    //para la inserción de participante
    @PostMapping("/{tallerId}/participantes/{participanteId}")
    public ResponseEntity<GetTallerDetail> inscribirParticipante(@PathVariable Long tallerId, @PathVariable Long participanteId) {
        return ResponseEntity.status(201).body(
                GetTallerDetail.of(tallerService.inscribirParticipante(tallerId, participanteId)));
    }

    @GetMapping("/{tallerId}/participantes")
    public ResponseEntity<List<GetParticipanteList>> getParticipantesDeTaller(@PathVariable Long tallerId) {
        List<Participante> result = tallerService.getParticipantesDeTaller(tallerId);
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetParticipanteList::of)
                        .toList()
        );
    }

    @DeleteMapping("/{tallerId}/participantes/{participanteId}")
    public ResponseEntity<Void> eliminarInscripcion(@PathVariable Long tallerId, @PathVariable Long participanteId) {
        tallerService.eliminarInscripcion(tallerId, participanteId);
        return ResponseEntity.noContent().build();
    }
}
