package com.portafolio.literalura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.criteria.CriteriaBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)

public class DatosAutor {
    @JsonAlias("name")
    private String nombre;
    @JsonAlias ("birth_year")
    private Integer fechaDeNacimiento;
    @JsonAlias ("death_year")
    private Integer fechaMuerte;

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

    public String getNombre() {
        return nombre;
    }

    public Integer fechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public Integer fechaMuerte() {
        return fechaMuerte;
    }
}
