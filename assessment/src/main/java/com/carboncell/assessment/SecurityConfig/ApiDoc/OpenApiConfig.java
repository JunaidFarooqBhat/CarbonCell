package com.carboncell.assessment.SecurityConfig.ApiDoc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Junaid Farooq Bhat",
                        email = "junaidbhat6258@gmail.com"
                ),
                description = "This application is designed to offer secure communication between clients and our services.It uses h2 database which is inmemory db. It leverages JWT (JSON Web Tokens) for authentication, ensuring that each request is authenticated and authorized.\n" +
                        "\n" +
                        "clients can register new accounts  and log in . Additionally, authenticated users have access to private endpoints, including retrieving their Ethereum balance, getting the list of(entries) public apis and logging out ."
                        ,
                title = "Carbon Cell Assessment-Junaid Farooq Bhat"
        ),
        servers = @Server(
                description = "Local Env",
                url="http://localhost:8081"
        ),
        security = @SecurityRequirement(
                name = "BearerAuth"
        )
)
@SecurityScheme(
        name = "BearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
