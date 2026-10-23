package com.salesianos.dam.journaltracker.controller;

import com.salesianos.dam.journaltracker.dto.organizador.EditOrganizadorDto;
import com.salesianos.dam.journaltracker.dto.organizador.GetOrganizadorDetail;
import com.salesianos.dam.journaltracker.dto.organizador.GetOrganizadorList;
import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.service.OrganizadorService;
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
@RequestMapping("/api/organizador")
@Tag(name = "Organizador Controller", description = "Gestión de los organizadores de talleres culturales")
public class OrganizadorController {

    private final OrganizadorService organizadorService;

    @Operation(
            summary = "Obtener todos los organizadores",
            description = "Permite obtener todos los organizadores registrados"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listado de organizadores",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = GetOrganizadorList.class
                                            )
                                    ),
                                    examples = @ExampleObject(
                                            name = "Listado de organizadores",
                                            summary = "Ejemplo de organizador registrado",
                                            value = """
                    [
                      {
                        "id": 15,
                        "nombre": "Pepe",
                        "email": "pepe@gmail.com"
                      },
                      {
                        "id": 16,
                        "nombre": "Pepa",
                        "email": "pepa@gmail.com"
                      },
                      {
                        "id": 17,
                        "nombre": "Francisco",
                        "email": "francisco@gmail.com"
                      }
                    ]
                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No hay organizadores con esos criterios de búsqueda",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(
                                            implementation = ProblemDetail.class
                                    ),
                                    examples = @ExampleObject(
                                            name = "Error 404",
                                            summary = "Mensaje de error informando de que no hay organizadores",
                                            value = """
                                                    
                                                    {
                                                      "detail": "Organizadores not found",
                                                      "instance": "/api/organizador",
                                                      "status": 404,
                                                      "title": "Recurso no encontrado",
                                                      "type": "https://example.com/errors/organizador-not-found"
                                                    }
                                                    
                                                    """
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<GetOrganizadorList>> getAllOrganizadores() {
        List<Organizador> result = organizadorService.getAllOrganizadores();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetOrganizadorList::of)
                        .toList());
    }

    @Operation(
            summary = "Obtener un organizador por id",
            description = "Permite obtener el detalle de un organizador concreto, incluyendo sus talleres"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organizador encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = GetOrganizadorList.class
                                            )
                                    ),
                                    examples = @ExampleObject(
                                            name = "Organizador con id",
                                            summary = "Ejemplo de detalle de organizador con id",
                                            value = """
                                            [
                                              {
                                                "id": 15,
                                                "nombre": "Pepe",
                                                "email": "pepe@gmail.com"
                                              }
                                            ]
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No existe un organizador con ese id",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(
                                            implementation = ProblemDetail.class
                                    ),
                                    examples = @ExampleObject(
                                            name = "Error 404",
                                            summary = "Mensaje de error informando de que el organizador no ha sido encontrado",
                                            value = """
                                                    {
                                                      "detail": "Organizadores no encontrado con id 99",
                                                      "instance": "/api/organizador/99",
                                                      "status": 404,
                                                      "title": "Organizador no encontrado",
                                                      "type": "https://example.com/errors/organizador-not-found"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<GetOrganizadorDetail> getOrganizadorById(@PathVariable Long id) {
        //return ResponseEntity.ok(GetOrganizadorList.of(organizadorService.getOrganizadorById(id)));
        //return ResponseEntity.ok(GetOrganizadorDetail.of(organizadorService.getOrganizadorById(id)));
        return ResponseEntity.ok(GetOrganizadorDetail.of(organizadorService.getOrganizadorById(id)));
    }

    @Operation(
            summary = "Crear un organizador",
            description = "Da de alta un nuevo organizador. Nombre y email son obligatorios"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Organizador creado",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = GetOrganizadorDetail.class
                                            )
                                    ),
                                    examples = @ExampleObject(
                                            name = "Organizador creado",
                                            summary = "Nuevo organizador creado con nombre y email obligatorios",
                                            value = """
                                            [
                                              {
                                                "id": 15,
                                                "nombre": "Pepe",
                                                "email": "pepe@gmail.com",
                                                "talleres": []
                                              }
                                            ]
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos de entrada no válidos (nombre o email vacíos, email mal formado)",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(
                                            implementation = ProblemDetail.class
                                    ),
                                    examples = @ExampleObject(
                                            name = "Error 404",
                                            summary = "Mensaje de error informando de que no hay organizadores con el id",
                                            value = """
                                                    {
                                                      "detail": "Organizador no fue creado",
                                                      "instance": "/api/organizador/99",
                                                      "status": 400,
                                                      "title": "Organizador no creado",
                                                      "type": "https://example.com/errors/organizador-no-creado"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<GetOrganizadorDetail> addOrganizador (@Valid @RequestBody EditOrganizadorDto editOrganizadorDto){
        return ResponseEntity.status(201).body(
                GetOrganizadorDetail.of(organizadorService.addOrganizador(editOrganizadorDto))
        );
    }

    @Operation(
            summary = "Actualizar un organizador",
            description = "Modifica el nombre y el email de un organizador existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Organizador actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetOrganizadorDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un organizador con ese id",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<GetOrganizadorDetail> updateOrganizador(@PathVariable Long id, @Valid @RequestBody EditOrganizadorDto editOrganizadorDto){
        return ResponseEntity.ok(
                GetOrganizadorDetail.of(organizadorService.updateOrganizador(id, editOrganizadorDto)));
    }

    @Operation(
            summary = "Eliminar un organizador",
            description = "Elimina un organizador por id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Organizador eliminado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un organizador con ese id",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizador(@PathVariable Long id){
        organizadorService.deleteOrganizadorById(id);
        return ResponseEntity.noContent().build();
    }

    // Swagger UI: http://localhost:8080/swagger-ui/index.html
}
