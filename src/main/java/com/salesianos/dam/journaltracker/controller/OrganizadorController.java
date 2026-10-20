package com.salesianos.dam.journaltracker.controller;

import com.salesianos.dam.journaltracker.dto.organizador.GetOrganizadorDetail;
import com.salesianos.dam.journaltracker.dto.organizador.GetOrganizadorList;
import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.repository.OrganizadorRepository;
import com.salesianos.dam.journaltracker.service.OrganizadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
















}
