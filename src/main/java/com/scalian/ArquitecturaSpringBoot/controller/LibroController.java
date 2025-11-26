package com.scalian.ArquitecturaSpringBoot.controller;

import com.scalian.ArquitecturaSpringBoot.model.dto.LibroDTO;
import com.scalian.ArquitecturaSpringBoot.model.entity.Libro;
import com.scalian.ArquitecturaSpringBoot.service.LibroService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
@AllArgsConstructor
public class LibroController {

    private final LibroService libroService;

    @GetMapping
    public List<LibroDTO> obtenerTodas() {
        return libroService.obtenerTodas();
    }

    @PostMapping
    public Libro crearLibro(@RequestBody LibroDTO libroDTO) {
        return libroService.crearLibro(libroDTO);
    }

    //Query
    @GetMapping("/buscar")
    public List<LibroDTO> getLibroXNombre(@RequestParam String nombre) {
        return libroService.getLibroXNombre(nombre);
    }

    //JPQL
    @GetMapping("/mayores")
    public List<LibroDTO> findByPaginasGreaterThan(@RequestParam int paginas) {
        return libroService.findByPaginasGreaterThan(paginas);
    }

}
