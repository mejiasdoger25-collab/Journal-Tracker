package com.salesianos.dam.journaltracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organizador {

    @Id @GeneratedValue
    private Long id;

    private String nombre;
    private String email;

    @OneToMany
    private Taller taller;
}

