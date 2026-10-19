package com.salesianos.dam.journaltracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Taller {

    @Id @GeneratedValue
    private Long id;

    private String titulo;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    @ManyToOne
    private Organizador organizador;

    @ManyToMany
    private Participante participante;
}
