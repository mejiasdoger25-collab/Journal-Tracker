package com.salesianos.dam.journaltracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "organizador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organizador {

    @Id @GeneratedValue
    private Long id;

    private String nombre;
    private String email;

    @OneToMany(mappedBy = "organizador")
    private List<Taller> talleres;}

