package com.salesianos.dam.journaltracker.service;

import com.salesianos.dam.journaltracker.dto.organizador.EditOrganizadorDto;
import com.salesianos.dam.journaltracker.error.notfound.OrganizadorNotFoundException;
import com.salesianos.dam.journaltracker.error.notfound.TallerNotFoundException;
import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.repository.OrganizadorRepository;
import com.salesianos.dam.journaltracker.repository.TallerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizadorService {

    private final OrganizadorRepository organizadorRepository;
    private final TallerRepository tallerRepository;

    public List<Organizador> getAllOrganizadores (){
        List<Organizador> result = organizadorRepository.findAll();
        if (result.isEmpty()) {
            throw new OrganizadorNotFoundException();
        }
        return result;
    }

    public Organizador getOrganizadorById (Long id) {
        return organizadorRepository.findById(id)
                .orElseThrow(() -> new OrganizadorNotFoundException(id));
    }

    /*public List<Organizador> getOrganizadorByTaller(Long id){
        List<Organizador> result = organizadorRepository.findByTallerId(id);
        return result;

        if (result.isEmpty()) {
            throw new OrganizadorNotFoundException("Organizador not found with taller id " + id);
        }
        cambiado pues el method ha sido movido del repo al service tras ser creado
    }*/

    public Organizador addOrganizador(EditOrganizadorDto editOrganizadorDto){
        //add validation w/ 1annotations or 2method (1>2)
        Organizador o = editOrganizadorDto.to();

        //Taller t = tallerRepository.findById(id).map(t -> { // add cosas dto when is created})

        return organizadorRepository.save(o);
    }


    public Organizador updateOrganizador(Long id, EditOrganizadorDto editOrganizadorDto){
        return organizadorRepository.findById(id)
                .map(o -> {
                    o.setNombre(editOrganizadorDto.nombre());
                    o.setEmail(editOrganizadorDto.email());
                    o.setTalleres(editOrganizadorDto.talleres());
                    return organizadorRepository.save(o);
                }).orElseThrow(() -> new OrganizadorNotFoundException(id));
    }


    public void deleteOrganizadorById (Long id){
        if (!tallerRepository.existsById(id)) {
            throw new TallerNotFoundException(id);
        }
        organizadorRepository.deleteById(id);
    }

    /*public void deleteOrganizador (Organizador organizador){
        organizadorRepository.delete(organizador);
    }*/

    //Implementar con los dtos, posteriormente pasar a anotaciones; private void validateOrganizador(OrganizadorDto editOrganizador){}











}
