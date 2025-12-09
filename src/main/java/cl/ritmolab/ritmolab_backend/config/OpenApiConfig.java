package cl.ritmolab.ritmolab_backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "RitmoLab API",
                version = "v1",
                description = "API REST del backend de RitmoLab (productos, categorías, carritos, usuarios)"
        )
)
public class OpenApiConfig {
}
