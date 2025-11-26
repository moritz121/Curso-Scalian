package com.scalian.ArquitecturaSpringBoot.service;

import java.util.List;
import com.scalian.ArquitecturaSpringBoot.model.dto.LibroDTO;
import com.scalian.ArquitecturaSpringBoot.model.entity.Libro;
import com.scalian.ArquitecturaSpringBoot.repository.LibroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroService libroService;

    // Test vars
    Long test_id = 1L;
    String test_title = "Test Título";
    String test_author = "Test Autor";
    int test_n_pages = 200;

    @Test
    void crear_libro_success() {

        LibroDTO dto = new LibroDTO(test_title, test_author, test_n_pages);

        Libro libroGuardado = new Libro();
        libroGuardado.setId(test_id);
        libroGuardado.setTitulo(test_title);
        libroGuardado.setAutor(test_author);
        libroGuardado.setPaginas(test_n_pages);

        when(libroRepository.save(any(Libro.class))).thenReturn(libroGuardado);
        Libro service_libro_output = libroService.crearLibro(dto);

        assertNotNull(service_libro_output.getId());
        assertEquals(test_title, service_libro_output.getTitulo());
        assertEquals(test_author, service_libro_output.getAutor());
        assertEquals(test_n_pages, service_libro_output.getPaginas());

        verify(libroRepository, times(1)).save(any(Libro.class));
    }

    @Test
    void crearLibro_empty_title_throws_error() {

        LibroDTO dto = new LibroDTO("", test_author, test_n_pages);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> libroService.crearLibro(dto)
        );
        assertEquals("Title cannot be empty", exception.getMessage());
    }

    @Test
    void crearLibro_lessthan_1_page_throws_error() {

        LibroDTO dto = new LibroDTO(test_title, test_author, 0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> libroService.crearLibro(dto)
        );
        assertEquals("Nº of pages cannot be <1", exception.getMessage());
    }

    @Test
    void getLibroXAutor_delegates2repo() {

        Libro libro1 = new Libro(test_id, test_title, test_author, test_n_pages);
        Libro libro2 = new Libro(2L, "Test Título 2", test_author, 300);

        when(libroRepository.getLibroXAutor(test_author)).thenReturn(List.of(libro1, libro2));

        List<LibroDTO> service_libro_output = libroService.getLibroXAutor(test_author);

        assertEquals(2, service_libro_output.size());
        assertEquals(test_title, service_libro_output.get(0).getTitulo());
        assertEquals("Test Título 2", service_libro_output.get(1).getTitulo());

        verify(libroRepository, times(1)).getLibroXAutor(test_author);
    }

    @Test
    void verify_repo_interaction() {

        LibroDTO dto = new LibroDTO(test_title, test_author, test_n_pages);
        Libro libroGuardado = new Libro(test_id, test_title, test_author, test_n_pages);
        when(libroRepository.save(any(Libro.class))).thenReturn(libroGuardado);
        Libro service_libro_output = libroService.crearLibro(dto);

        verify(libroRepository, times(1)).save(any(Libro.class));
        verify(libroRepository).save(argThat(libro ->
                libro.getTitulo().equals(dto.getTitulo()) &&
                        libro.getAutor().equals(dto.getAutor()) &&
                        libro.getPaginas() == dto.getPaginas()
        ));
    }


}
