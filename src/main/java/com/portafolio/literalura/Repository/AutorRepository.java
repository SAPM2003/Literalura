package com.portafolio.literalura.Repository;

import com.portafolio.literalura.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.JpqlQueryBuilder;

import java.util.List;
import java.util.Optional;

public interface AutorRepository  extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :anio AND a.fechaMuerte>= :anio OR a.fechaMuerte IS NULL")
    List<Autor> obtenerAutoresVivosEnDeterminadoAnio(Integer anio);
}
