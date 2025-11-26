package com.scalian.ArquitecturaSpringBoot.service;

import com.scalian.ArquitecturaSpringBoot.model.dto.LibroDTO;
import com.scalian.ArquitecturaSpringBoot.model.dto.PersonaDTO;
import com.scalian.ArquitecturaSpringBoot.model.entity.Libro;
import com.scalian.ArquitecturaSpringBoot.repository.LibroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;

    public List<LibroDTO> obtenerTodas() {
        return libroRepository.findAll().stream()
                .map(p -> {
                    LibroDTO libroDTO = new LibroDTO();
                    libroDTO.setTitulo(p.getTitulo());
                    libroDTO.setAutor(p.getAutor());
                    libroDTO.setPaginas(p.getPaginas());
                    return libroDTO;
                })
                .collect(Collectors.toList());

    }

    public Libro crearLibro(LibroDTO libroDTO) {
        Libro libro = new Libro(null, libroDTO.getTitulo(), libroDTO.getAutor(), libroDTO.getPaginas());
        return libroRepository.save(libro);
    }

    //Query
    public List<LibroDTO> getLibroXNombre(String nombre) {
        return libroRepository.getLibroXNombre(nombre).stream()
                .map(this::convertirADTO)
                .toList();
    }

    //Query
    public List<LibroDTO> findByPaginasGreaterThan(Integer paginas) {
        return libroRepository.findByPaginasGreaterThan(paginas).stream()
                .map(this::convertirADTO)
                .toList();
    }

    //Debería estar en un Mapper
    private LibroDTO convertirADTO(Libro libro) {
        return new LibroDTO(libro.getTitulo(), libro.getAutor(), libro.getPaginas());
    }
}
