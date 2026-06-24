package com.ipn.mx.celex.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI celexOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API CELEX - Cursos de Idiomas")
                        .description("Sistema de gestion para cursos de idiomas. IPN - ESCOM. "
                                + "Incluye envio de correos via SMTP (Gmail) y Jakarta Mail.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo CELEX")
                                .email("alexamejia1611@gmail.com")));
    }
}
