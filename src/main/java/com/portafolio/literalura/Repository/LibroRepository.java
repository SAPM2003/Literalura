package com.portafolio.literalura.Repository;

import com.portafolio.literalura.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloIgnoreCase(String titulo);
    List<Libro> findByIdioma(String idioma);

    boolean existsByTitulo(String titulo);

}

