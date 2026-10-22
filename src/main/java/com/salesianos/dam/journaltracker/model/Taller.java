package com.salesianos.dam.journaltracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "taller")
@Data
@EqualsAndHashCode(of = "id") //@data genera equals/hashCode con todos los campos(listas de relación incluidas). Al +de1, para comprobar o para delete, como están vinculadas las instancias, equals() entra en bucle -> StackOverflowError
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
    private List<Participante> participantes;
}
