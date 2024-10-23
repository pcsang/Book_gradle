package com.example.demo.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@EnableWebMvc
public class ConfigSwagger {

    @Bean
    public GroupedOpenApi publicApi() {
        String[] paths = {"/api/v1/**"};
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(openApiCustomizer())
                .build();
    }

    @Bean
    public GroupedOpenApi privateApi() {
        String[] paths = {"/api/v2/**"};
        return GroupedOpenApi.builder()
                .group("v2")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(openApiCustomizer())
                .build();
    }

    private static OpenApiCustomizer openApiCustomizer() {
        return openApi -> openApi
                .info(new Info().title("Controller")
                        .description("Document for Controller")
                        .version("V.0.0.1")
                        .license(new License().name("Apache 2.0").url("http://localhost:8080/v1/api/book")))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring boot 3.0.0")
                        .url("http://localhost:8080/v1/api/book"));

    }

}
