
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

## Endpoints disponibles

- `POST /api/usuarios`: Registro de usuario (RF001-01)
- `GET /api/usuarios`: Listado de usuarios (RF001-02)

## Documentación técnica

La API está documentada automáticamente usando Swagger. Se puede revisar y probar los endpoints directamente desde la interfaz web generada.

## Validaciones

Se implementó la validación del formato del Email.

## Estado del proyecto

✅ En desarrollo activo.  
✅ Funcionalidades básicas completas.  
🛠 Se prevé agregar autenticación mediante token en próximas iteraciones, así como las pruebas unitarias que por razones de tiempo no llegamos a incluir en esta entrega.

## Contribuciones

Contribuciones académicas bienvenidas. Para sugerencias, abrir un *Merge Request* o contacto directo vía GitLab.

## Autor

- TOKEN
- Universidad Tecnológica - Área de Programación 2025

## Licencia

Este proyecto se distribuye con fines educativos y de formación. Licencia a definir según destino final.
