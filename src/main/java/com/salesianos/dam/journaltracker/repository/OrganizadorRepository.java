package com.salesianos.dam.journaltracker.repository;

import com.salesianos.dam.journaltracker.model.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizadorRepository
    extends JpaRepository<Organizador, Long> {

    //List<Organizador> findByTallerId(Long id); -> cambiado al service
}
