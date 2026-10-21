package com.salesianos.dam.journaltracker.repository;

import com.salesianos.dam.journaltracker.model.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticpanteRepository
        extends JpaRepository<Organizador, Long> {


}
