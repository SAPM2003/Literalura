package com.portafolio.literalura.Principal;

import com.portafolio.literalura.Model.*;
import com.portafolio.literalura.Repository.AutorRepository;
import com.portafolio.literalura.Repository.LibroRepository;
import com.portafolio.literalura.Service.ConsumoAPI;
import com.portafolio.literalura.Service.ConvierteDatos;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class Principal implements CommandLineRunner {

    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private final ConsumoAPI consumoAPI;
    private final ConvierteDatos conversor;
    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public Principal(ConsumoAPI consumoAPI, ConvierteDatos conversor, AutorRepository autorRepository, LibroRepository libroRepository) {
        this.consumoAPI = consumoAPI;
        this.conversor = conversor;
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }


    public void muestraElMenu() {

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos en un determinado año
                    5 - listar libros por idioma
                    0 - salir
                    """;
            System.out.println(menu);
            System.out.println("Elija una opcion");


            var entrada = teclado.nextLine();

            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException t) {
                System.out.println("Error, por favor ingresa un numero, no letras");
                opcion = -1;
                continue;

            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnUnDeterminadoAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando app...");
                    break;

                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el titulo del libro que quieres buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        var respuesta = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (respuesta.getResults() != null && !respuesta.getResults().isEmpty()) {
            DatosLibro datosLibro = respuesta.getResults().get(0);

            Optional<Libro> libroExistente = libroRepository.findByTituloIgnoreCase(datosLibro.titulo());
            if (libroExistente.isPresent()) {
                System.out.println("El libro: " + datosLibro.titulo()+ " ya se encuentra registrado");
                return;
            }

            Libro libro = new Libro();
            libro.setTitulo(datosLibro.titulo());
            libro.setIdioma(datosLibro.idiomas().get(0));
            libro.setNumeroDescargas(datosLibro.numeroDescargas());
            List<Autor> autoresEntidad = new ArrayList<>();

            for (DatosAutor dAutor : datosLibro.autores()) {
                Optional<Autor> autorExistente = autorRepository.findByNombreIgnoreCase(dAutor.getNombre());

                Autor autor;
                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                } else {
                    autor = new Autor(dAutor);
                    autor = autorRepository.save(autor);
                }
                autoresEntidad.add(autor);
            }
            libro.setAutores(autoresEntidad);
            libroRepository.save(libro);

            System.out.println("*******LIBRO ENCONTRADO*******");
            System.out.println(libro);
            System.out.println("*******************************");
        } else {
            System.out.println("No se encontraron resultados");
        }
    }
    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
        } else {

            System.out.println("\n******LIBROS REGISTRADOS******\n");

            libros.forEach(System.out::println);
            System.out.println("\n******************************\n");
    }}

    private void listarAutoresRegistrados(){
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados");
        } else {
            System.out.println("\n******AUTORES REGISTRADOS******\n");
            autores.forEach(System.out::println);
            System.out.println("\n*******************************\n");
        }
    }

    private void listarAutoresVivosEnUnDeterminadoAnio() {
        System.out.println("Ingresa el año que deseas consultar: ");
        try {
            var anioBusqueda = teclado.nextInt();
            teclado.nextLine();
            List<Autor> autoresVivos = autorRepository.obtenerAutoresVivosEnDeterminadoAnio(anioBusqueda);
            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos.");
            } else {
                autoresVivos.forEach(a -> {
                    System.out.println("Nombre: " + a.getNombre());
                    System.out.println("Nacimiento: " + a.getFechaDeNacimiento());
                    System.out.println("Fallecimiento: " + (a.getFechaMuerte() != null ? a.getFechaMuerte() : "N/A (Aún vivo)"));
                    System.out.println("Libros" + a.getLibros());
                    System.out.println(" ");
                });
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: por favor ingresa el año en formato numerico (ej: 1920)");
            teclado.nextLine();
        }

    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar los libros: 
                es - Español
                en - Inglés
                fr - francés
                pt - portugues
                """);
        var idiomaElegido = teclado.nextLine();
        List<Libro> librosPorIdioma = libroRepository.findByIdioma(idiomaElegido);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma seleccionado");
        } else {
            System.out.println("******LIBROS EN " + idiomaElegido+"******");
            librosPorIdioma.forEach(System.out::println);

        }

    }

    @Override
    public void run(String... args) throws Exception {
        muestraElMenu();
    }
}
