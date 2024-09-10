package com.marcos.joao.springmongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcos.joao.springmongodb.model.Empregado;

public interface EmpregadoRepository extends MongoRepository<Empregado, String> {

}
