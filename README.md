

# Literalura: 


Catálogo de libros en el cual la persona usuaria puede registrar libros en una base de datos y recibir información sobre estos libros que ya están registrados en la base de datos.

Este proyecto utiliza la API de Gutendex para obtener datos bibliográficos y los almacena en una base de datos relacional para su gestión.

# Funcionalidades: 

Búsqueda inteligente: Permite buscar libros por título a través de una API externa.

Persistencia de datos: Los libros y autores encontrados se guardan en PostgreSQL.

Control de duplicados: El sistema verifica si un libro o autor ya existe antes de registrarlo, evitando datos repetidos.

Filtros avanzados:

Listado de todos los libros registrados.

Listado de autores registrados.

Búsqueda de autores vivos en un determinado año.

Filtrado de libros por idioma (ES, EN, FR, PT).

# Tecnologías: 

Java 25

Spring Boot 3.4.0

Spring Data JPA

PostgreSQL

Jackson (para el procesamiento de JSON)

# Retos Técnicos y Aprendizajes: 

Gestión de Duplicidad: Uno de los mayores desafíos fue asegurar la integridad de la base de datos. Se implementó la restricción @Column(unique = true) en la entidad Libro y validaciones lógicas en el repositorio usando existsByTitulo para evitar registros redundantes.

Relaciones Complejas: Se utilizó una relación @ManyToMany para vincular libros con múltiples autores, manejando la carga de datos con FetchType.EAGER para asegurar que la información esté disponible en la consola.

Conectividad de Base de Datos: Configuración de perfiles de Spring (application-dev.properties) para manejar de forma segura las credenciales locales de PostgreSQL.

# Configuración: 

Para ejecutar este proyecto localmente, es necesario crear una base de datos llamada literalura en PostgreSQL y configurar las credenciales en el archivo src/main/resources/application-dev.properties:

Properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.profiles.active=dev

#Autor: 

Samir Pastran
Linkedin: https://www.linkedin.com/in/samir-pastran/
