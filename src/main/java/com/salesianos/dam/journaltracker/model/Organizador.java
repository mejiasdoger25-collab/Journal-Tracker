package com.salesianos.dam.journaltracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "organizador")
@Data
@EqualsAndHashCode(of = "id") //@data genera equals/hashCode con todos los campos(listas de relación incluidas). Al +de1, para comprobar o para delete, como están vinculadas las instancias, equals() entra en bucle -> StackOverflowError
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organizador {

    @Id @GeneratedValue
    private Long id;

    private String nombre;
    private String email;

    @OneToMany(mappedBy = "organizador")
    @JsonIgnore //le dice a Jackson que este campo no se incluya cuando se serializa un Organizador/Participante dentro de otra respuesta
    private List<Taller> talleres;}

