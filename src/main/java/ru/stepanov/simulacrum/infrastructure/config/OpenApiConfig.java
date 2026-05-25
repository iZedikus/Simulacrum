package ru.stepanov.simulacrum.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Simulacrum API",
                description = "API for account, transaction and consent simulation",
                version = "1.0.0",
                contact = @Contact(name = "Simulacrum Team")
        ),
        tags = {
                @Tag(name = "Accounts"),
                @Tag(name = "Transactions"),
                @Tag(name = "Consents"),
                @Tag(name = "Admin")
        }
)
public class OpenApiConfig {
}
