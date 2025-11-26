package com.scalian.ArquitecturaSpringBoot.controller;
import com.scalian.ArquitecturaSpringBoot.ArquitecturaSpringBootApplication;
import com.scalian.ArquitecturaSpringBoot.model.dto.LibroDTO;
import com.scalian.ArquitecturaSpringBoot.model.entity.Libro;
import com.scalian.ArquitecturaSpringBoot.service.LibroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

@WebMvcTest(controllers = {LibroController.class, ApiExceptionHandler.class})
class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LibroService libroService;

    Long test_id = 1L;
    String test_title = "Test Título";
    String test_author = "Test Autor";
    int test_n_pages = 200;
    
    @Test
    void crearLibro_returns_201() throws Exception {
        LibroDTO dto = new LibroDTO(test_title, test_author, test_n_pages);
        Libro saved = new Libro(test_id, test_title, test_author, test_n_pages);

        when(libroService.crearLibro(any(LibroDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value(test_title))
                .andExpect(jsonPath("$.autor").value(test_author))
                .andExpect(jsonPath("$.paginas").value(test_n_pages));
    }

    @Test
    void crearLibro_invalid_returns_400() throws Exception {
        LibroDTO dto = new LibroDTO("", test_author, 0);

        when(libroService.crearLibro(any(LibroDTO.class)))
                .thenThrow(new IllegalArgumentException("Invalid data"));

        mockMvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid data"));
    }

    @Test
    void buscarXAutor_returns_200() throws Exception {
        String autor = test_author;

        List<LibroDTO> libros = List.of(
                new LibroDTO(test_title, test_author, test_n_pages),
                new LibroDTO("Test Título 2", test_author, 300)
        );

        when(libroService.getLibroXAutor(autor)).thenReturn(libros);

        mockMvc.perform(get("/api/libros/buscar")
                        .param("autor", autor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].titulo").value(test_title))
                .andExpect(jsonPath("$[1].titulo").value("Test Título 2"));
    }
}
