package ru.stepanov.simulacrum.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Симулякр - Система по имитации пользовательской активности",
                description = "API для управления аккаунтами, ПДА и транзакциями",
                version = "1.0.0"
        )
)
public class OpenApiConfig {
}
