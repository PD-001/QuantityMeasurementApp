package com.apps.quantitymeasurement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
            .info(new Info()
                .title("Quantity Measurement API")
                .version("1.0.0")
                .description(
                    "REST API for comparing, converting and calculating " +
                    "across length, weight, volume and temperature units. " +
                    "Login via Google OAuth2 at /oauth2/authorization/google " +
                    "to get a JWT token, then click Authorize below."
                )
                .contact(new Contact()
                    .name("Quantity Measurement App")
                )
            )
            .addSecurityItem(new SecurityRequirement()
                .addList(securitySchemeName))
            .components(new Components()
                .addSecuritySchemes(securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("Paste your JWT token here (without the 'Bearer' prefix)")
                )
            );
    }
}