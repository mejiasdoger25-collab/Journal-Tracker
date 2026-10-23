# 🎨 Talleres Culturales — API REST

API REST desarrollada con **Spring Boot** para gestionar los talleres culturales de un centro, las personas que los organizan y los participantes inscritos. El proyecto permite administrar las entidades principales y gestionar las inscripciones entre talleres y participantes.

> Proyecto académico de 2.º de Desarrollo de Aplicaciones Multiplataforma (DAM), curso 2026-2027.

---

## 📌 Índice

- [Funcionalidades](#-funcionalidades)
- [Modelo de datos](#-modelo-de-datos)
- [Tecnologías](#-tecnologías)
- [Requisitos y ejecución](#-requisitos-y-ejecución)
- [Documentación interactiva](#-documentación-interactiva)
- [Endpoints](#-endpoints)
- [Ejemplos de peticiones](#-ejemplos-de-peticiones)
- [Validaciones y errores](#-validaciones-y-errores)
- [Estructura del proyecto](#-estructura-del-proyecto)
- [Pruebas con Postman](#-pruebas-con-postman)

## ✨ Funcionalidades

- Crear, consultar, actualizar y eliminar **organizadores**.
- Crear, consultar, actualizar y eliminar **talleres**.
- Crear y listar **participantes**.
- Consultar talleres por organizador o participante.
- Consultar el organizador responsable de un taller.
- Inscribir participantes existentes en talleres existentes y eliminar inscripciones.
- Validar los datos recibidos y devolver errores estructurados mediante `ProblemDetail`.
- Consultar la documentación de la API generada con OpenAPI 3 y Swagger UI.

## 🧩 Modelo de datos

La API trabaja con tres entidades:

| Entidad | Campos principales | Relaciones |
|---|---|---|
| **Organizador** | `id`, `nombre`, `email` | Un organizador puede tener varios talleres. |
| **Taller** | `id`, `titulo`, `fechaHoraInicio`, `fechaHoraFin` | Cada taller pertenece a un organizador y puede tener varios participantes. |
| **Participante** | `id`, `nombreCompleto` | Un participante puede inscribirse en varios talleres. |

Las relaciones se representan mediante una asociación **uno a muchos** entre `Organizador` y `Taller`, y una asociación **muchos a muchos** entre `Taller` y `Participante`. La inscripción no constituye una entidad independiente.

Un taller nuevo se crea sin participantes inscritos. Para inscribir a alguien, tanto el taller como el participante deben existir previamente.

## 🛠️ Tecnologías

- **Java** y **Spring Boot** para la aplicación.
- **Spring Web** para los controladores REST.
- **Spring Data JPA / Hibernate** para el acceso a datos y el mapeo objeto-relacional.
- **H2 Database** como base de datos.
- **Jakarta Bean Validation** para validar las entradas.
- **OpenAPI 3 / Swagger UI** para documentar y explorar los endpoints.
- **Lombok** para reducir código repetitivo.
- **Maven** para la gestión del proyecto y sus dependencias.

## ▶️ Requisitos y ejecución

Necesitas tener instalado:

- Una versión del **JDK compatible con la configurada en el proyecto**.
- **Maven**, o el Maven Wrapper si está incluido en el repositorio.
- Un IDE compatible con proyectos Spring Boot, como IntelliJ IDEA, Eclipse o Spring Tool Suite (opcional).

### 1. Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd <CARPETA_DEL_PROYECTO>
```

Sustituye los valores entre `<...>` por la URL real del repositorio y el nombre de su carpeta.

### 2. Ejecutar la aplicación

Desde la carpeta raíz, donde se encuentra el `pom.xml`:

```bash
mvn spring-boot:run
```

También puedes abrir el proyecto desde tu IDE y ejecutar la clase principal `JournalTrackerApplication`.

Por defecto, la aplicación utiliza el puerto **8080**, por lo que la API estará disponible en:

```text
http://localhost:8080
```

### 3. Base de datos

La configuración actual utiliza **H2** y define la conexión en `src/main/resources/application.properties`.

- URL JDBC: `jdbc:h2:./db/basedatos`
- Usuario: `sa`
- Contraseña: vacía
- Consola web de H2 habilitada.
- `spring.jpa.hibernate.ddl-auto=create-drop`: el esquema se crea al iniciar y se elimina al cerrar la aplicación. **Los datos no están pensados para conservarse entre reinicios.**

La consola de H2 puede consultarse en `http://localhost:8080/h2-console` mientras la aplicación esté en ejecución. Para conectarte, utiliza la URL JDBC, el usuario y la contraseña indicados arriba.

## 📚 Documentación interactiva

Con la aplicación arrancada, abre Swagger UI:

**[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

Desde ahí puedes consultar los recursos, ver los parámetros y cuerpos esperados, revisar las respuestas documentadas y ejecutar peticiones directamente.

La especificación OpenAPI en formato JSON suele estar disponible en:

[http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 🌐 Endpoints

Todas las rutas se sirven bajo `http://localhost:8080`. Los cuerpos de las peticiones y respuestas utilizan JSON salvo que se indique lo contrario.

### Organizadores — `/api/organizador`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/organizador` | Lista los organizadores. |
| `GET` | `/api/organizador/{id}` | Obtiene un organizador por su ID, con sus datos de detalle. |
| `POST` | `/api/organizador` | Crea un organizador. |
| `PUT` | `/api/organizador/{id}` | Actualiza un organizador existente. |
| `DELETE` | `/api/organizador/{id}` | Elimina un organizador. |

**Campos de entrada:** `nombre` y `email` son obligatorios; el email debe tener un formato válido.

### Talleres — `/api/taller`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/taller` | Lista los talleres. |
| `GET` | `/api/taller/{id}` | Obtiene el detalle de un taller. |
| `GET` | `/api/taller/organizador/{id}` | Lista los talleres de un organizador. |
| `GET` | `/api/taller/participante/{id}` | Lista los talleres en los que participa una persona. |
| `GET` | `/api/taller/{id}/organizador` | Obtiene el organizador de un taller. |
| `POST` | `/api/taller` | Crea un taller. |
| `PUT` | `/api/taller/{id}` | Actualiza un taller. |
| `DELETE` | `/api/taller/{id}` | Elimina un taller. |

**Campos de entrada para crear o actualizar:** `titulo`, `fechaHoraInicio`, `fechaHoraFin` y `organizadorId`. Las fechas se envían como cadenas ISO 8601 de fecha y hora local, por ejemplo `2026-11-10T17:00:00`. El organizador indicado debe existir.

### Participantes — `/api/participante`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/participante` | Lista los participantes registrados. |
| `POST` | `/api/participante` | Crea un participante. |

El campo `nombreCompleto` es obligatorio. La gestión de participantes se limita a crear y listar, de acuerdo con el alcance del proyecto.

### Inscripciones — rutas anidadas en `/api/taller`

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/taller/{tallerId}/participantes/{participanteId}` | Inscribe a un participante existente en un taller existente. |
| `GET` | `/api/taller/{tallerId}/participantes` | Lista los participantes inscritos en el taller. |
| `DELETE` | `/api/taller/{tallerId}/participantes/{participanteId}` | Elimina la inscripción sin borrar el taller ni al participante. |

No se permiten inscripciones duplicadas. Si se elimina una inscripción que no existía, la operación no modifica los datos y devuelve `204 No Content`, siempre que el taller y el participante existan.

## 🧪 Ejemplos de peticiones

### Crear un organizador

`POST /api/organizador`

```json
{
  "nombre": "María López",
  "email": "maria.lopez@example.com"
}
```

### Crear un participante

`POST /api/participante`

```json
{
  "nombreCompleto": "Ana García"
}
```

### Crear un taller

`POST /api/taller`

```json
{
  "titulo": "Cerámica para principiantes",
  "fechaHoraInicio": "2026-11-10T17:00:00",
  "fechaHoraFin": "2026-11-10T19:00:00",
  "organizadorId": 1
}
```

El valor `organizadorId` debe corresponder al ID de un organizador creado previamente. Los IDs de los ejemplos son ilustrativos: utiliza los que devuelva tu propia aplicación.

### Inscribir a una persona

`POST /api/taller/1/participantes/1`

No requiere cuerpo JSON. En este ejemplo, `1` representa el ID del taller y el último `1` el ID del participante.

### Consultar las inscripciones de un taller

`GET /api/taller/1/participantes`

La respuesta es una lista JSON de participantes. Si el taller existe y todavía no tiene inscripciones, devuelve una lista vacía.

### Eliminar una inscripción

`DELETE /api/taller/1/participantes/1`

Elimina únicamente la relación entre ese taller y ese participante; ambos registros se conservan.

## 🛡️ Validaciones y errores

La API valida las entradas mediante Jakarta Bean Validation y una restricción personalizada para las fechas.

- El nombre del organizador y su email no pueden estar vacíos.
- El email del organizador debe tener un formato válido.
- El título del taller es obligatorio y no puede duplicarse. La comprobación ignora mayúsculas/minúsculas y se eliminan los espacios al principio y al final del título recibido.
- La fecha de inicio y la fecha de fin son obligatorias.
- La fecha de fin debe ser **estrictamente posterior** a la fecha de inicio.
- El nombre completo del participante es obligatorio.
- Un taller debe referenciar a un organizador existente.
- Una inscripción requiere que existan tanto el taller como el participante, y no puede repetirse.

Los errores de negocio se gestionan de forma centralizada y se devuelven mediante respuestas `ProblemDetail` (`application/problem+json`).

| Código HTTP | Significado habitual |
|---|---|
| `200 OK` | Consulta o actualización completada. |
| `201 Created` | Recurso o inscripción creada. |
| `204 No Content` | Eliminación completada sin cuerpo de respuesta. |
| `400 Bad Request` | Datos de entrada no válidos. |
| `404 Not Found` | No se encuentra el recurso solicitado. |
| `409 Conflict` | El título del taller ya existe o el participante ya está inscrito. |

Ejemplo orientativo de un conflicto por título duplicado:

```json
{
  "detail": "Ya existe un taller con el título 'Cerámica para principiantes'",
  "status": 409,
  "title": "Taller ya existe",
  "type": "https://example.com/errors/taller-already-exists"
}
```

El contenido exacto del error depende del endpoint y del motivo del fallo.

## 🗂️ Estructura del proyecto

El código fuente se organiza dentro del paquete `com.salesianos.dam.journaltracker`:

```text
src/
└── main/
    ├── java/com/salesianos/dam/journaltracker/
    │   ├── config/          # Configuración de validación
    │   ├── controller/      # Endpoints REST
    │   ├── dto/             # DTOs de entrada y salida
    │   ├── error/           # Excepciones y gestión global de errores
    │   ├── model/           # Entidades JPA
    │   ├── repository/      # Acceso a datos con Spring Data JPA
    │   ├── service/         # Lógica de negocio
    │   ├── validation/      # Validación personalizada de fechas
    │   └── JournalTrackerApplication.java
    └── resources/
        ├── messages/
        │   └── errors.properties
        └── application.properties
```

- **Controller:** recibe las peticiones HTTP y construye las respuestas.
- **Service:** centraliza las operaciones y las reglas de negocio.
- **Repository:** proporciona las operaciones de persistencia.
- **Model:** define las entidades y sus relaciones.
- **DTO:** separa los datos de entrada y salida de las entidades.
- **Error:** agrupa las excepciones propias y el tratamiento global de errores.
- **Validation:** contiene la validación personalizada de la coherencia temporal del taller.

## 📮 Pruebas con Postman

El repositorio debe incluir la colección de Postman exportada para probar los endpoints.

1. Abre Postman.
2. Pulsa **Import** y selecciona el archivo JSON de la colección incluido en el repositorio.
3. Arranca la API en `http://localhost:8080`.
4. Ejecuta primero las peticiones de creación de organizadores y participantes.
5. Usa los IDs obtenidos en las peticiones de creación de talleres e inscripciones.
6. Prueba también los casos de error: IDs inexistentes, datos obligatorios vacíos, fechas incoherentes, títulos duplicados e inscripciones repetidas.

Para que las pruebas sean reproducibles, recuerda que la configuración actual de H2 recrea el esquema al reiniciar la aplicación.

---

## 👤 Autoría

Proyecto individual realizado para los módulos de **Acceso a Datos** y **Programación de Servicios y Procesos**, dentro del ciclo de Desarrollo de Aplicaciones Multiplataforma (DAM), curso 2026-2027.
