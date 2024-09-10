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

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EmpregadoRepository empregadoRep;

    @GetMapping
    public ResponseEntity<List<Endereco>> getAllEnderecos(){
    	List<Endereco> enderecos = empregadoRep.findAll().stream()
    			.map(Empregado::getEndereco)
    			.filter(endereco -> endereco != null)
    			.collect(Collectors.toList());
    	return ResponseEntity.ok(enderecos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> getEnderecoById(@PathVariable String id){
    	Optional<Empregado> optionalEmpregado = empregadoRep.findById(id);
    	if(optionalEmpregado.isPresent()) {
    		return ResponseEntity.ok(optionalEmpregado.get().getEndereco());
    	}
    	return ResponseEntity.notFound().build();
    }
    
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
