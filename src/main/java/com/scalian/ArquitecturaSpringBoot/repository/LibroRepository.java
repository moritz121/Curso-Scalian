package com.scalian.ArquitecturaSpringBoot.repository;

import com.scalian.ArquitecturaSpringBoot.model.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    //JPA
    List<Libro> findByPaginasGreaterThan(int paginas);

    //JPQL
    @Query(value = "SELECT * FROM libro WHERE autor = :autor", nativeQuery = true)
    List<Libro> getLibroXAutor(@Param("autor") String autor);

}
