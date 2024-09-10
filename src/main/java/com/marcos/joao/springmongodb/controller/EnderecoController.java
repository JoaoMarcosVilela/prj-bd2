package com.marcos.joao.springmongodb.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.joao.springmongodb.model.Empregado;
import com.marcos.joao.springmongodb.model.Endereco;
import com.marcos.joao.springmongodb.repository.EmpregadoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EmpregadoRepository empregadoRep;

    @Operation(summary = "Obter todos os endereços", description = "Retorna a lista de todos os endereços associados aos empregados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereços encontrados",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Endereco.class))}),
        @ApiResponse(responseCode = "404", description = "Nenhum endereço encontrado", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Endereco>> getAllEnderecos(){
    	List<Endereco> enderecos = empregadoRep.findAll().stream()
    			.map(Empregado::getEndereco)
    			.filter(endereco -> endereco != null)
    			.collect(Collectors.toList());
    	return ResponseEntity.ok(enderecos);
    }
    
    @Operation(summary = "Obter endereço por ID", description = "Retorna o endereço de um empregado com o ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereço encontrado",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Endereco.class))}),
        @ApiResponse(responseCode = "404", description = "Empregado não encontrado ou sem endereço", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> getEnderecoById(@PathVariable String id){
    	Optional<Empregado> optionalEmpregado = empregadoRep.findById(id);
    	if(optionalEmpregado.isPresent()) {
    		return ResponseEntity.ok(optionalEmpregado.get().getEndereco());
    	}
    	return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Atualizar endereço", description = "Atualiza o endereço de um empregado pelo ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Endereço atualizado",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Endereco.class))}),
        @ApiResponse(responseCode = "404", description = "Empregado não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Endereco> updateEndereco(@PathVariable String id, @RequestBody Endereco endereco){
    	Optional<Empregado> optionalEmpregado = empregadoRep.findById(id);
    	if(optionalEmpregado.isPresent()) {
    		Empregado empregado = optionalEmpregado.get();
    		empregado.setEndereco(endereco);
    		empregadoRep.save(empregado);
    		return ResponseEntity.ok(empregado.getEndereco());
    	}
    	return ResponseEntity.notFound().build();
    	
    }
    
    @Operation(summary = "Deletar endereço", description = "Remove o endereço de um empregado pelo ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Endereço removido com sucesso", content = @Content),
        @ApiResponse(responseCode = "404", description = "Empregado não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnderecoById(@PathVariable String id){
    	Optional<Empregado> optionalEmpregado = empregadoRep.findById(id);
    	if(optionalEmpregado.isPresent()) {
    		Empregado empregado = optionalEmpregado.get();
    		empregado.setEndereco(null);
    		empregadoRep.save(empregado);
    		return ResponseEntity.noContent().build();
    	}
    	return ResponseEntity.notFound().build();
    }
    
    
    
}
