package com.salesianos.dam.journaltracker.controller;

import com.salesianos.dam.journaltracker.dto.participante.EditParticipante;
import com.salesianos.dam.journaltracker.dto.participante.GetParticipanteList;
import com.salesianos.dam.journaltracker.model.Participante;
import com.salesianos.dam.journaltracker.repository.ParticipanteRepository;
import com.salesianos.dam.journaltracker.service.ParticipanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/participante")
@Tag(name = "Participante Controller", description = "CRUD básico de participantes (crear y listar), según el enunciado")
public class ParticipanteController {

    //controller incompleto(básico) pues el enunciado lo define así

    private final ParticipanteRepository participanteRepository;
    private final ParticipanteService participanteService;

    @Operation(summary = "Obtener todos los participantes",
            description = "Devuelve el listado completo de participantes registrados")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de participantes",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetParticipanteList.class)),
                            examples = @ExampleObject(
                                    name = "Listado de participantes",
                                    value = """
                                            [
                                              { "id": 4, "nombreCompleto": "Ana García", "taller": [] },
                                              { "id": 5, "nombreCompleto": "Luis Pérez", "taller": [] }
                                            ]
                                            """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<GetParticipanteList>> getAllParticipantes(){
        List<Participante> result = participanteService.getAllParticipantes();
        return  ResponseEntity.ok(
                result
                        .stream()
                        .map(GetParticipanteList::of)
                        .toList()
        );
    }

    @Operation(summary = "Crear un participante",
            description = "Da de alta un nuevo participante. El nombre completo es obligatorio")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Participante creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetParticipanteList.class))),
            @ApiResponse(responseCode = "400",
                    description = "Datos de entrada no válidos (nombre completo vacío)",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<GetParticipanteList> addPartticipante (@Valid @RequestBody EditParticipante editParticipante){
        return ResponseEntity.status(201).body(
                GetParticipanteList.of(participanteService.addParticipante(editParticipante))
        );
    }

//http://localhost:8080/swagger-ui/index.html
}
