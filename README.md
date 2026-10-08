# 🎬 Screenmatch

Aplicación backend desarrollada en Java con **Spring Boot**, creada como parte de las rutas de aprendizaje de **Alura Latam**. Este sistema está diseñado para la gestión, consulta y procesamiento de información sobre películas y series, implementando persistencia de datos con bases de datos relacionales.

---

## 🚀 Tecnologías y Herramientas Utilizadas
* **Java** (Versión 17 o superior)
* **Spring Boot**
* **Spring Data JPA / Hibernate**
* **PostgreSQL** (Base de datos relacional)
* **Maven** (Gestor de dependencias)
* **Gson** (Procesamiento y mapeo de datos JSON)
* **Git & GitHub** (Control de versiones)

---

## 📁 Estructura del Proyecto
El código fuente sigue una arquitectura organizada en capas dentro del paquete principal `com.aluracursos.screenmatch`:
* **`Enum/`**: Definiciones de valores constantes y enumeraciones (géneros, categorías, etc.).
* **`Main/`**: Lógica de ejecución y flujos principales de interacción por consola.
* **`Model/`**: Clases de dominio y entidades de la aplicación (Película, Serie, Episodio, Título).
* **`repository/`**: Interfaces de acceso a datos mediante Spring Data JPA.
* **`service/`**: Capa de servicios encargada de la lógica de negocio y procesamiento de datos.
* **`ScreenmatchApplication.java`**: Clase principal que inicializa el servidor y el contexto de Spring Boot.

---

## ⚙️ Configuración y Ejecución Local

Sigue estos pasos para poner en marcha el proyecto en tu entorno local:

## Configurar las credenciales de la base de datos

    1.- Dirígete a la ruta src/main/resources/.

    2.- Duplica el archivo de ejemplo application.properties.example y renómbralo a application.properties.

    3.- Ajusta los parámetros con tus credenciales locales

        spring.application.name=screenmatch
        spring.datasource.url=jdbc:postgresql://localhost:5433/screenmatch
        spring.datasource.username=tu_usuario
        spring.datasource.password=tu_contraseña
        spring.datasource.driver-class-name=org.postgresql.Driver
        spring.jpa.hibernate.ddl-auto=update

### 🔒 Buenas Prácticas de Seguridad y Control de Versiones
        * Este repositorio implementa un archivo .gitignore optimizado que garantiza:

        * Protección de datos sensibles: El archivo application.properties con contraseñas locales nunca se sube a GitHub.

        * Plantilla de configuración: Se incluye application.properties.example para que cualquier colaborador conozca las propiedades necesarias sin exponer datos privados.

        * Limpieza de archivos innecesarios: Se excluyen directorios de compilación (target/) y archivos temporales del entorno de desarrollo.

        * Desarrollado con ☕ y Java como parte del programa de formación de Alura Latam.

### Clonar el repositorio
```bash
git clone [https://github.com/tu-usuario/screenmatch.git](https://github.com/tu-usuario/screenmatch.git)
cd screenmatch

---
