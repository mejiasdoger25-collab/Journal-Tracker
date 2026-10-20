package com.salesianos.dam.journaltracker.service;

import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.repository.OrganizadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizadorService {

    private final OrganizadorRepository organizadorRepository;

    public List<Organizador> getAllOrganizadores (){
        List<Organizador> result = organizadorRepository.findAll();
        /*if (result.isEmpty()) {
            throw new OrganizadorNotFoundException();
        } para el siguiente bloque de excepciones*/
        return result;

        /*forma más limpia para no usar exception:return organizadorRepository.findAll();*/
    }

    public Organizador getOrganizadorById (Long id) {
        return organizadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organizador no encontrado con id: " + id));
        //cambiar la excepción general por creada propia
    }

    public Organizador addOrganizador(Organizador organizador){
        return organizadorRepository.save(organizador);
    }

    /*public Organizador updateOrganizador(Long id){
        return organizadorRepository.findById(id)
                .map(o -> {
                    // continuar cuando esté el dto
                })
    }*/

    public void deleteOrganizador (Long id){
        organizadorRepository.deleteById(id);
    }

    //Implementar con los dtos, posteriormente pasar a anotaciones; private void validateOrganizador(OrganizadorDto editOrganizador){}
}
