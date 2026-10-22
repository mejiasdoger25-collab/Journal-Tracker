package com.salesianos.dam.journaltracker.controller;

import com.salesianos.dam.journaltracker.dto.organizador.EditOrganizadorDto;
import com.salesianos.dam.journaltracker.dto.organizador.GetOrganizadorDetail;
import com.salesianos.dam.journaltracker.dto.organizador.GetOrganizadorList;
import com.salesianos.dam.journaltracker.model.Organizador;
import com.salesianos.dam.journaltracker.repository.OrganizadorRepository;
import com.salesianos.dam.journaltracker.service.OrganizadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/organizador")
public class OrganizadorController {

    private final OrganizadorRepository organizadorRepository;
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
                        "id": 15,
                        "nombre": "Pepa",
                        "email": "pepa@gmail.com"
                      },
                      {
                        "id": 15,
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
            description = "Permite obtener el organizador con un id deseado"
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
                                            name = "Organizador con id",
                                            summary = "Ejemplo de organizador registrado con id",
                                            value = """
                    [
                      {
                        "id": 15,
                        "nombre": "Pepe",
                        "email": "pepe@gmail.com"
                      },
                      {
                        "id": 15,
                        "nombre": "Pepa",
                        "email": "pepa@gmail.com"
                      },
                      {
                        "id": 15,
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
                                            summary = "Mensaje de error informando de que no hay organizadores con el id",
                                            value = """
                                                    
                                                    {
                                                      "detail": "Organizadores not found",
                                                      "instance": "/api/organizador/{id}",
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
    @GetMapping("/{id}")
    public ResponseEntity<GetOrganizadorDetail> getOrganizadorById(@PathVariable Long id) {
        //return ResponseEntity.ok(GetOrganizadorList.of(organizadorService.getOrganizadorById(id)));
        //return ResponseEntity.ok(GetOrganizadorDetail.of(organizadorService.getOrganizadorById(id)));
        return ResponseEntity.ok(GetOrganizadorDetail.of(organizadorService.getOrganizadorById(id)));
    }

    /*
    @GetMapping("/taller/{id}")
    public ResponseEntity<List<GetOrganizadorList>> getOrganizadorByTaller(@PathVariable Long id){
        List<Organizador> result = organizadorService.getOrganizadorByTaller(id);
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetOrganizadorList::of)
                        .toList()
        );
    } cambiado pues el method ha sido movido del repo al service tras ser creado
    */

    @Operation(
            summary = "Agregar un nuevo organizador",
            description = "Permite agrgar un organizador nuevo"
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
                                            name = "Organizador con id",
                                            summary = "Ejemplo de organizador registrado con id",
                                            value = """
                    [
                      {
                        "id": 15,
                        "nombre": "Pepe",
                        "email": "pepe@gmail.com"
                      },
                      {
                        "id": 15,
                        "nombre": "Pepa",
                        "email": "pepa@gmail.com"
                      },
                      {
                        "id": 15,
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
                                            summary = "Mensaje de error informando de que no hay organizadores con el id",
                                            value = """
                                                    
                                                    {
                                                      "detail": "Organizadores not found",
                                                      "instance": "/api/organizador/{id}",
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
    @PostMapping
    public ResponseEntity<GetOrganizadorDetail> addOrganizador (@Valid @RequestBody EditOrganizadorDto editOrganizadorDto){
        return ResponseEntity.status(201).body(
                GetOrganizadorDetail.of(organizadorService.addOrganizador(editOrganizadorDto))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetOrganizadorDetail> updateOrganizador(@PathVariable Long id, @Valid @RequestBody EditOrganizadorDto editOrganizadorDto){
        return ResponseEntity.ok(
                GetOrganizadorDetail.of(organizadorService.updateOrganizador(id, editOrganizadorDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizador(@PathVariable Long id){
        organizadorService.deleteOrganizadorById(id);
        return ResponseEntity.noContent().build();
    }









}
