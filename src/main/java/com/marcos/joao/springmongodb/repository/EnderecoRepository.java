package com.marcos.joao.springmongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcos.joao.springmongodb.model.Endereco;

public interface EnderecoRepository extends MongoRepository<Endereco, String> {

}
