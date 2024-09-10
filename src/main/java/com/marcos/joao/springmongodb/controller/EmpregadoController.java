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


@RestController
@RequestMapping("/empregados")
public class EmpregadoController {

    @Autowired
    private EmpregadoService empregadoService;

    @GetMapping
    public List<Empregado> listarTodos() {
        return empregadoService.listarTodos();
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Empregado> buscarPorId(@PathVariable String id) {
        Optional<Empregado> empregado = empregadoService.buscarPorId(id);
        return empregado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Empregado criarEmpregado(@RequestBody Empregado empregado) {
        return empregadoService.salvar(empregado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empregado> atualizarEmpregado(@PathVariable String id, @RequestBody Empregado empregado) {
        if (!empregadoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        empregado.setId(id);
        return ResponseEntity.ok(empregadoService.salvar(empregado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpregado(@PathVariable String id) {
        if (!empregadoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        empregadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}