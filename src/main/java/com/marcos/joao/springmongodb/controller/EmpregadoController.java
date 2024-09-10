package com.marcos.joao.springmongodb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.joao.springmongodb.model.Empregado;
import com.marcos.joao.springmongodb.service.EmpregadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/empregados")
public class EmpregadoController {

    @Autowired
    private EmpregadoService empregadoService;

    @Operation(summary = "Lista todos os empregados")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso"),
    })
    @GetMapping
    public List<Empregado> listarTodos() {
        return empregadoService.listarTodos();
    }
    
    @Operation(summary = "Busca um empregado pelo ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Empregado encontrado", 
                     content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Empregado.class)) }),
        @ApiResponse(responseCode = "404", description = "Empregado não encontrado") 
    })
    @GetMapping("/{id}")
    public ResponseEntity<Empregado> buscarPorId(@PathVariable String id) {
        Optional<Empregado> empregado = empregadoService.buscarPorId(id);
        return empregado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Cria um novo empregado")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Empregado criado com sucesso", 
                     content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Empregado.class)) }),
    })
    @PostMapping
    public Empregado criarEmpregado(@RequestBody Empregado empregado) {
        return empregadoService.salvar(empregado);
    }

    @Operation(summary = "Atualiza um empregado existente pelo ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Empregado atualizado com sucesso", 
                     content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Empregado.class)) }),
        @ApiResponse(responseCode = "404", description = "Empregado não encontrado") 
    })
    @PutMapping("/{id}")
    public ResponseEntity<Empregado> atualizarEmpregado(@PathVariable String id, @RequestBody Empregado empregado) {
        if (!empregadoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        empregado.setId(id);
        return ResponseEntity.ok(empregadoService.salvar(empregado));
    }

    @Operation(summary = "Deleta um empregado pelo ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Empregado deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empregado não encontrado") 
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpregado(@PathVariable String id) {
        if (!empregadoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        empregadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}