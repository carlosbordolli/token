
# TOKEN_PINFRA

## Descripción del proyecto

**TOKEN_PINFRA** es una API RESTful desarrollada con Spring Boot orientada a la gestión de usuarios. Fue creada como parte del proyecto académico de la Universidad Tecnológica (Área de Programación - 2025). Esta aplicación permite registrar nuevos usuarios, listar los existentes y validar datos de entrada, conectándose a una base de datos PostgreSQL.

## Tecnologías utilizadas

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Swagger / OpenAPI
- JUnit 5
- Mockito
- Maven

## Instalación y ejecución

1. Clona el repositorio:
```bash
git clone https://git.utec.edu.uy/carlos.bordolli/token_pinfra.git
cd token_pinfra
```

2. Configura tu base de datos PostgreSQL:
    - Crea una base de datos llamada `asur`.
    - Ejecutá el script de base de datos disponible en el archivo `script_bd.sql` para crear todas las tablas necesarias.

3. Edita `application.properties`:
```properties
spring.application.name=PINFRANOW
spring.datasource.url=jdbc:postgresql://localhost:5432/asur
spring.datasource.username=postgres
spring.datasource.password=admin

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

4. Ejecuta el proyecto:
```bash
./mvnw spring-boot:run
```

5. Accede a la documentación Swagger:
```
http://localhost:8080/swagger-ui.html
```

## Despliegue en Railway

Para desplegar esta aplicación en Railway, seguí los siguientes pasos:

1. **Crear una base de datos PostgreSQL** desde Railway.
2. **Ejecutar el script de base de datos** disponible en el archivo `script_bd.sql` para crear todas las tablas necesarias.
3. **Conectar Railway con GitHub** y seleccionar el repositorio correspondiente para desplegar automáticamente el backend.
4. Railway detectará automáticamente el proyecto como una aplicación Spring Boot si está configurado correctamente en `pom.xml`.

Una vez desplegado, podés acceder a la API desde el dominio proporcionado por Railway.

### Consideraciones para Railway

- Railway crea automáticamente el entorno y las variables necesarias para ejecución. Asegurate de configurar las siguientes variables de entorno en el dashboard del proyecto:
  - `SPRING_DATASOURCE_URL`
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`
- El script de base de datos (`script_bd.sql`) debe ejecutarse antes del primer despliegue si la base no está vacía. Podés hacerlo conectándote a la base PostgreSQL que Railway genera desde el panel de SQL o usando una herramienta como DBeaver o PgAdmin.
- No es necesario clonar el proyecto localmente para que funcione en Railway: basta con vincular el repositorio directamente desde GitHub y Railway se encargará del resto.



## Endpoints disponibles

- `GET /api/auditoria`
- `GET /api/pagos/{id}`
- `PUT /api/pagos/{id}`
- `DELETE /api/pagos/{id}`
- `GET /api/pagos`
- `POST /api/pagos`
- `GET /api/pagos/usuario/{idUsuario}/fecha`
- `GET /api/pagos/fecha`
- `GET /api/pagos/actividad/{idActividad}`
- `GET /api/funcionalidades/{id}`
- `PUT /api/funcionalidades/{id}`
- `DELETE /api/funcionalidades/{id}`
- `GET /api/funcionalidades`
- `POST /api/funcionalidades`
- `GET /api/tipo-actividad/{id}`
- `PUT /api/tipo-actividad/{id}`
- `DELETE /api/tipo-actividad/{id}`
- `GET /api/tipo-actividad`
- `POST /api/tipo-actividad`
- `PUT /api/inscripciones/cancelar`
- `GET /api/inscripciones`
- `POST /api/inscripciones`
- `POST /api/inscripciones/reporte/tipo-actividad`
- `POST /api/inscripciones/reporte/actividades`
- `GET /api/inscripciones/{idUsuario}/{idActividad}`
- `DELETE /api/inscripciones/{idUsuario}/{idActividad}`
- `GET /api/auxiliares`
- `POST /api/auxiliares`
- `GET /api/auxiliares/{id}`
- `DELETE /api/auxiliares/{id}`
- `GET /api/auxiliares/usuario/{idUsuario}`
- `PUT /api/reservas/cancelar/{id}`
- `POST /api/reservas`
- `GET /api/reservas/reporte/por-fecha`
- `GET /api/reservas/reporte/por-espacio/{idEspacio}`
- `GET /api/usuarios/{id}`
- `PUT /api/usuarios/{id}`
- `DELETE /api/usuarios/{id}`
- `PUT /api/usuarios/me`
- `GET /api/usuarios`
- `POST /api/usuarios`
- `GET /api/usuarios/sin-verificar`
- `GET /api/perfiles/{id}`
- `PUT /api/perfiles/{id}`
- `DELETE /api/perfiles/{id}`
- `GET /api/perfiles`
- `POST /api/perfiles`
- `GET /api/espacios/{id}`
- `PUT /api/espacios/{id}`
- `DELETE /api/espacios/{id}`
- `GET /api/espacios`
- `POST /api/espacios`
- `GET /api/accesos-funcionalidades`
- `POST /api/accesos-funcionalidades`
- `GET /api/accesos-funcionalidades/perfil/{idPerfil}`
- `DELETE /api/accesos-funcionalidades/{idPerfil}/{idFuncionalidad}`
- `GET /api/actividades/{id}`
- `PUT /api/actividades/{id}`
- `DELETE /api/actividades/{id}`
- `PUT /api/actividades/cancelar/{id}`
- `GET /api/actividades`
- `POST /api/actividades`
- `GET /api/actividades/obtenerPorInscripcion`
- `POST /api/auth/login`
- `GET /`


## POSTMAN

Se encuentran disponibles dos colecciones Postman para facilitar las pruebas de los endpoints de la API en diferentes entornos:

- 🧪 Colección para entorno local: `asur_api_collection_localhost.json`
- ☁️ Colección para entorno de producción (Railway): `asur_api_collection_railway.json`



## Documentación técnica

La API está documentada automáticamente usando Swagger. Se puede revisar y probar los endpoints directamente desde la interfaz web generada.

## Estado del proyecto
  
✅ Funcionalidades básicas completas.  
🛠 Se prevén nuevas versiones incorporando mejoras en base a nuevas necesidades que puedan ir surgiendo

## Contribuciones

Contribuciones académicas bienvenidas. Para sugerencias, abrir un *Merge Request* o contacto directo vía GitLab.

## Autor

- TOKEN
- Universidad Tecnológica - Área de Programación 2025

## Licencia

Este proyecto se distribuye con fines educativos y de formación. Licencia a definir según destino final.
