package com.marcos.joao.springmongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
public class SpringmongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringmongodbApplication.class, args);
	}

}
