package com.scalian.ArquitecturaSpringBoot;

import com.scalian.ArquitecturaSpringBoot.model.entity.Persona;
import com.scalian.ArquitecturaSpringBoot.repository.PersonaRepository;
import com.scalian.ArquitecturaSpringBoot.repository.LibroRepository;
import com.scalian.ArquitecturaSpringBoot.model.entity.Libro;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class ArquitecturaSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArquitecturaSpringBootApplication.class, args);
	}

    @Bean
    @Profile("!test")
    public CommandLineRunner initPersonas(PersonaRepository repositorio) {
        return args -> {
            repositorio.save(new Persona(null, "Álvaro", 30));
            repositorio.save(new Persona(null, "Lucía", 25));
        };
    }

    @Bean
    @Profile("!test")
    public CommandLineRunner initLibros(LibroRepository repositorio) {
        return args -> {
            repositorio.save(new Libro(null, "Título 1", "Autor 1", 360));
            repositorio.save(new Libro(null, "Título 2", "Autor 2", 420));
        };
    }

}
