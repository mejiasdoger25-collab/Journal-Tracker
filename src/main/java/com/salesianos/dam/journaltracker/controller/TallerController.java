package com.salesianos.dam.journaltracker.controller;

import com.salesianos.dam.journaltracker.dto.taller.EditTallerDto;
import com.salesianos.dam.journaltracker.dto.taller.GetTallerDetail;
import com.salesianos.dam.journaltracker.dto.taller.GetTallerList;
import com.salesianos.dam.journaltracker.model.Taller;
import com.salesianos.dam.journaltracker.repository.TallerRepository;
import com.salesianos.dam.journaltracker.service.TallerService;
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
    public ResponseEntity<GetTallerDetail> addTaller (@RequestBody EditTallerDto editTallerDto){
        return ResponseEntity.status(201).body(
                GetTallerDetail.of(tallerService.addTaller(editTallerDto))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetTallerDetail> updateTaller(@PathVariable Long id, @RequestBody EditTallerDto editTallerDto){
        return ResponseEntity.ok(
                GetTallerDetail.of(tallerService.updateTaller(id, editTallerDto))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaller(@PathVariable Long id) {
        tallerService.deleteTallerById(id);
        return ResponseEntity.noContent().build();
    }

}
