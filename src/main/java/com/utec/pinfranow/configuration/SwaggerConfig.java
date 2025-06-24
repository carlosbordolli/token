package com.utec.pinfranow.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(

        info = @Info(
                title = "API - Gestión del Sistema de ASUR",
                description = "API RESTful para gestionar operaciones CRUD de ASUR",
                termsOfService = "Los términos del servicio los encontrará en nuestra página web",
                contact = @Contact (
                        name= "TOKEN",
                        url = "https://utec.edu.uy",
                        email = "token@token.com.uy"
                ),
                license = @License(
                        name = "UTEC Licence",
                        url = "https://utec.edu.uy/licenciaUTEC",
                        identifier = "UTEC"
                ),
                version = "1.0.0"



        ),
        servers = {
                @Server (
                        description = "Desarrollo",
                        url = "http://localhost:8080"
                ),
				@Server (
                        description = "Railway",
                        url = "https://token-production-8078.up.railway.app"
                ),

        }

)
public class SwaggerConfig {
}
