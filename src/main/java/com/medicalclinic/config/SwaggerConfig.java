package com.medicalclinic.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author claudio.vilas
 * date: 08/2023
 */

@Configuration
public class SwaggerConfig {
    @Value("${application.version}")
    private String version;
    @Bean
    public GroupedOpenApi api(){
        return GroupedOpenApi.builder()
                .group("clinical")
                .packagesToScan("com.medicalclinic")
                .build();
    }

    @Bean
    public OpenAPI springShopOpemapi(){
        return new OpenAPI()
                /*.addSecurityItem(new SecurityRequirement().addList(securityScemeName))
                .components(new Components())*/
                .info(new Info().title("Gestion Clinica Medica")
                        .description("Gestor de Clinica Medica")
                        .version(version));
    }
}
