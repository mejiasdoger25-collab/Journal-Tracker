package com.salesianos.dam.journaltracker.repository;

import com.salesianos.dam.journaltracker.model.Taller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TallerRepository
        extends JpaRepository<Taller, Long> {

    List<Taller> findByOrganizadorId(Long id);
    List<Taller> findByParticipantesId(Long id);
}
