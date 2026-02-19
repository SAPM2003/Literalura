package com.portafolio.literalura.Model;

import jakarta.persistence.*;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaMuerte;

    @ManyToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(Integer fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public Autor() {}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.getNombre();
        this.fechaDeNacimiento = (datosAutor.fechaDeNacimiento() != null) ?
                datosAutor.fechaDeNacimiento() : null;
        this.fechaMuerte = (datosAutor.fechaMuerte() != null) ?
                datosAutor.fechaMuerte() : null;
    }
    @Override
    public String toString() {
        String titulosLibros = (libros!= null) ? libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(", ")) : "Ninguno";
        return String.format("""
                        ******AUTOR******
                        Nombre: %s
                        Fecha de nacimiento: %s
                        Fecha de muerte: %s
                        Libros: %s
                        """,
                nombre,
                fechaDeNacimiento != null ? fechaDeNacimiento : "Desconocido",
                fechaMuerte != null ? fechaMuerte : "N/A (Sigue vivo o sin datos)",
                titulosLibros);
}}
