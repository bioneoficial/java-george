package com.javageorge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("http://localhost:8080" + contextPath)
                                .description("Local Server")))
                .info(new Info()
                        .title("Sistema de Gestão Escolar - API")
                        .description("API para gerenciamento de alunos, professores, disciplinas e matrículas")
                        .version("1.0")
                        .contact(new Contact().name("Java George")
                                .email("contato@javageorge.com")
                                .url("https://javageorge.com"))
                        .license(new License().name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
