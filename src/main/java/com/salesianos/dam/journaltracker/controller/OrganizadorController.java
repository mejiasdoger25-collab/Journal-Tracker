package com.salesianos.dam.journaltracker.controller;

import com.salesianos.dam.journaltracker.dto.organizador.EditOrganizadorDto;
import com.salesianos.dam.journaltracker.dto.organizador.GetOrganizadorDetail;
import com.salesianos.dam.journaltracker.dto.organizador.GetOrganizadorList;
import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.repository.OrganizadorRepository;
import com.salesianos.dam.journaltracker.service.OrganizadorService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/organizador")
public class OrganizadorController {

    private final OrganizadorRepository organizadorRepository;
    private final OrganizadorService organizadorService;

    @GetMapping
    public ResponseEntity<List<GetOrganizadorList>> getAllOrganizadores() {
        List<Organizador> result = organizadorService.getAllOrganizadores();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetOrganizadorList::of)
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetOrganizadorDetail> getOrganizadorById(@PathVariable Long id) {
        //return ResponseEntity.ok(GetOrganizadorList.of(organizadorService.getOrganizadorById(id)));
        //return ResponseEntity.ok(GetOrganizadorDetail.of(organizadorService.getOrganizadorById(id)));
        return ResponseEntity.ok(GetOrganizadorDetail.of(organizadorService.getOrganizadorById(id)));
    }

    @GetMapping("/taller/{id}")
    public ResponseEntity<List<GetOrganizadorList>> getOrganizadorByTaller(@PathVariable Long id){
        List<Organizador> result = organizadorService.getOrganizadorByTaller(id);
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetOrganizadorList::of)
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<GetOrganizadorDetail> addOrganizador (@RequestBody EditOrganizadorDto editOrganizadorDto){
        return ResponseEntity.status(201).body(
                GetOrganizadorDetail.of(organizadorService.addOrganizador(editOrganizadorDto))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetOrganizadorDetail> updateOrganizador(@PathVariable Long id, @RequestBody EditOrganizadorDto editOrganizadorDto){
        return ResponseEntity.ok(
                GetOrganizadorDetail.of(organizadorService.updateOrganizador(id, editOrganizadorDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizador(@PathVariable Long id){
        organizadorService.deleteOrganizadorById(id);
        return ResponseEntity.noContent().build();
    }









}
