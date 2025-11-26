package com.scalian.ArquitecturaSpringBoot.integration;

import com.scalian.ArquitecturaSpringBoot.model.dto.LibroDTO;
import com.scalian.ArquitecturaSpringBoot.model.entity.Libro;
import com.scalian.ArquitecturaSpringBoot.repository.LibroRepository;
import com.scalian.ArquitecturaSpringBoot.service.LibroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

// No tengo docker en el ordenador de Scalian y no puedo conectarlo con el WSL2. Esto es lo que creo que habría que hacer.
// No lo he podido comprobar si funciona. Se lo he pasado a Chatgpt para que le echara un vistazo y él parece creer que
// esto funcionaría. Más no he podido comprobar.

@Testcontainers
@SpringBootTest
public class LibroIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("password");

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroService libroService;

    // Integrar propiedades mediante @DynamicPropertySource
    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Test
    void testCrearYRecuperarLibro() {
        // Crear libro
        LibroDTO libroDTO = new LibroDTO("Test Título", "Test Autor", 200);
        Libro creado = libroService.crearLibro(libroDTO);

        // Recuperarlo
        Optional<Libro> encontrado = libroRepository.findById(creado.getId());
        assertThat(encontrado).isPresent();

        // verificar datos
        Libro libro = encontrado.get();
        assertThat(libro.getTitulo()).isEqualTo("Test Título");
        assertThat(libro.getAutor()).isEqualTo("Test Autor");
        assertThat(libro.getPaginas()).isEqualTo(200);
    }
}
