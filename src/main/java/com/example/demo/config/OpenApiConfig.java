package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI registroJornadaOpenApi() {
        return new OpenAPI().info(new Info()
                .title("API Registro de Jornada")
                .version("v1")
                .description("Gestión de departamentos, empleados y fichajes"));
    }
}
