package com.salesianos.dam.journaltracker.controller;

import com.salesianos.dam.journaltracker.dto.organizador.GetOrganizadorDetail;
import com.salesianos.dam.journaltracker.dto.participante.GetParticipanteList;
import com.salesianos.dam.journaltracker.dto.taller.EditTallerDto;
import com.salesianos.dam.journaltracker.dto.taller.GetTallerDetail;
import com.salesianos.dam.journaltracker.dto.taller.GetTallerList;
import com.salesianos.dam.journaltracker.model.Participante;
import com.salesianos.dam.journaltracker.model.Taller;
import com.salesianos.dam.journaltracker.repository.TallerRepository;
import com.salesianos.dam.journaltracker.service.TallerService;
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
@RequestMapping("/api/taller")
@Tag(name = "Taller Controller", description = "CRUD de talleres y gestión de las inscripciones de participantes")
public class TallerController {

    private final TallerRepository tallerRepository;
    private final TallerService tallerService;

    @Operation(summary = "Obtener todos los talleres",
            description = "Devuelve el listado completo de talleres")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de talleres",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetTallerList.class)),
                            examples = @ExampleObject(
                                    name = "Listado de talleres",
                                    value = """
                                            [
                                              {
                                                "id": 3,
                                                "titulo": "Cerámica para principiantes",
                                                "fechaHoraInicio": "2026-11-10T17:00:00",
                                                "fechaHoraFin": "2026-11-10T19:00:00"
                                              }
                                            ]
                                            """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<GetTallerList>> getAllTalleres() {
        List<Taller> result = tallerService.getAllTalleres();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetTallerList::of)
                        .toList()
        );
    }

    @Operation(summary = "Obtener un taller por id",
            description = "Devuelve el detalle de un taller, incluyendo su organizador y sus participantes")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Taller encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTallerDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un taller con ese id",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "detail": "Taller no encontrado con id 99",
                                      "instance": "/api/taller/99",
                                      "status": 404,
                                      "title": "Taller no encontrado",
                                      "type": "https://example.com/errors/taller-not-found"
                                    }
                                    """))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetTallerDetail> getTallerById(@PathVariable Long id) {
        return ResponseEntity.ok(GetTallerDetail.of(tallerService.getTallerById(id)));
    }

    @Operation(summary = "Obtener los talleres de un organizador",
            description = "Devuelve todos los talleres organizados por el organizador indicado")
    @ApiResponse(responseCode = "200",
            description = "Listado de talleres del organizador")
    @GetMapping("/organizador/{id}")
    public ResponseEntity<List<GetTallerList>> getTallerByOrganizador(@PathVariable Long id){
        List<Taller> result = tallerService.getTallerbyOrganizador(id);
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetTallerList::of)
                        .toList()
        );
    }

    @Operation(summary = "Obtener el organizador de un taller",
            description = "Devuelve el organizador responsable del taller indicado")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Organizador del taller"),
            @ApiResponse(responseCode = "404",
                    description = "No existe un taller con ese id",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}/organizador")
    public ResponseEntity<GetOrganizadorDetail> getOrganizadorDeTaller(@PathVariable Long id) {
        return ResponseEntity.ok(GetOrganizadorDetail.of(tallerService.getOrganizadorDeTaller(id)));
    }

    @Operation(summary = "Obtener los talleres de un participante",
            description = "Devuelve todos los talleres en los que está inscrito el participante indicado")
    @ApiResponse(responseCode = "200",
            description = "Listado de talleres del participante")
    @GetMapping("/participante/{id}")
    public ResponseEntity<List<GetTallerList>> getTallerByParticipante(@PathVariable Long id){
        List<Taller> result = tallerService.getTallerbyParticipante(id);
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetTallerList::of)
                        .toList()
        );
    }

    @Operation(summary = "Crear un taller",
            description = "Da de alta un nuevo taller. El título debe ser único y fechaHoraFin posterior a fechaHoraInicio")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Taller creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTallerDetail.class))),
            @ApiResponse(responseCode = "400",
                    description = "Datos de entrada no válidos (campos obligatorios o fechas incoherentes)",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "El organizador indicado no existe",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe un taller con ese título",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "detail": "Ya existe un taller con el título 'Cerámica para principiantes'",
                                      "status": 409,
                                      "title": "Taller ya existe",
                                      "type": "https://example.com/errors/taller-already-exists"
                                    }
                                    """)))
    })
    @PostMapping
    public ResponseEntity<GetTallerDetail> addTaller (@Valid @RequestBody EditTallerDto editTallerDto){
        return ResponseEntity.status(201).body(
                GetTallerDetail.of(tallerService.addTaller(editTallerDto))
        );
    }

    @Operation(summary = "Actualizar un taller",
            description = "Modifica los datos de un taller existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Taller actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTallerDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "No existe el taller o el organizador indicado",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe otro taller con ese título",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<GetTallerDetail> updateTaller(@PathVariable Long id, @Valid @RequestBody EditTallerDto editTallerDto){
        return ResponseEntity.ok(
                GetTallerDetail.of(tallerService.updateTaller(id, editTallerDto))
        );
    }

    @Operation(summary = "Eliminar un taller", description = "Elimina un taller por id")
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Taller eliminado"),
            @ApiResponse(responseCode = "404",
                    description = "No existe un taller con ese id",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaller(@PathVariable Long id) {
        tallerService.deleteTallerById(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Inscribir un participante en un taller",
            description = "Vincula un participante existente a un taller existente. No se permite repetir la misma inscripción")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Inscripción realizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetTallerDetail.class))),
            @ApiResponse(responseCode = "404", description = "No existe el taller o el participante indicado",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "El participante ya está inscrito en ese taller",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "detail": "El participante 4 ya está inscrito en el taller 3",
                                      "status": 409,
                                      "title": "Participante ya inscrito",
                                      "type": "https://example.com/errors/participante-ya-inscrito"
                                    }
                                    """)))
    })
    //para la inserción de participante
    @PostMapping("/{tallerId}/participantes/{participanteId}")
    public ResponseEntity<GetTallerDetail> inscribirParticipante(@PathVariable Long tallerId, @PathVariable Long participanteId) {
        return ResponseEntity.status(201).body(
                GetTallerDetail.of(tallerService.inscribirParticipante(tallerId, participanteId)));
    }

    @Operation(summary = "Listar los participantes de un taller",
            description = "Devuelve los participantes inscritos en el taller indicado; lista vacía si no tiene ninguno")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Listado de participantes inscritos",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetParticipanteList.class)))),
            @ApiResponse(responseCode = "404",
                    description = "No existe un taller con ese id",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{tallerId}/participantes")
    public ResponseEntity<List<GetParticipanteList>> getParticipantesDeTaller(@PathVariable Long tallerId) {
        List<Participante> result = tallerService.getParticipantesDeTaller(tallerId);
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetParticipanteList::of)
                        .toList()
        );
    }

    @Operation(summary = "Eliminar la inscripción de un participante en un taller",
            description = "Desvincula un participante de un taller conservando ambos. Si no estaban vinculados, no hace nada")
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Inscripción eliminada (o no existía, sin cambios)"),
            @ApiResponse(responseCode = "404",
                    description = "No existe el taller o el participante indicado",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{tallerId}/participantes/{participanteId}")
    public ResponseEntity<Void> eliminarInscripcion(@PathVariable Long tallerId, @PathVariable Long participanteId) {
        tallerService.eliminarInscripcion(tallerId, participanteId);
        return ResponseEntity.noContent().build();
    }

//http://localhost:8080/swagger-ui/index.html
}
