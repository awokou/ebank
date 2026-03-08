package com.server.api.ebank.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "EBANK", version = "1.0", description = "Documentation API REST pour le système EBANK", contact = @Contact(name = "Admin EBANK", email = "admin@gmail.com", url = "http://localhost:8080"), license = @License(name = "EBANK License", url = "http://localhost:8080"), termsOfService = "Utilisation réservée au système EBANK"), servers = {
        @Server(description = "Development", url = "http://localhost:8080"),
        @Server(description = "Production", url = "https://api.ebank.com")
}, security = {
        @SecurityRequirement(name = "bearerAuth")
})
@SecurityScheme(name = "bearerAuth", description = "JWT Authorization header using the Bearer scheme", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {
}
