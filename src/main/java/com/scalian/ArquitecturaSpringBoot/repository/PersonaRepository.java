package com.scalian.ArquitecturaSpringBoot.repository;

import com.scalian.ArquitecturaSpringBoot.model.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    //JPA
    List<Persona> findByEdadGreaterThan(int edad);

    //JPQL
    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Persona> buscarPorNombre(@Param("filtro") String filtro);

    //NativeSQL
    @Query(value = "SELECT * FROM persona WHERE edad = :edad", nativeQuery = true)
    List<Persona> buscarPorEdadExacta(@Param("edad") int edad);

}
