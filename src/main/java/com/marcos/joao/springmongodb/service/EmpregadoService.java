package com.marcos.joao.springmongodb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcos.joao.springmongodb.model.Empregado;
import com.marcos.joao.springmongodb.model.Endereco;
import com.marcos.joao.springmongodb.repository.EmpregadoRepository;
import com.marcos.joao.springmongodb.repository.EnderecoRepository;

@Service
public class EmpregadoService {

	@Autowired
	private EmpregadoRepository empregadoRep;
	
	public List<Empregado> listarTodos() {
        return empregadoRep.findAll();
    }

    public Optional<Empregado> buscarPorId(String id) {
        return empregadoRep.findById(id);
    }

    public Empregado salvar(Empregado empregado) {
        return empregadoRep.save(empregado);
    }

    public void deletar(String id) {
    	empregadoRep.deleteById(id);
    }
    

}
