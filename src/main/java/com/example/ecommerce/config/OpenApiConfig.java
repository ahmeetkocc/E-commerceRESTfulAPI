package com.example.ecommerce.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ecommerceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("E-Ticaret API")
                        .description("E-Ticaret RESTful API Dokümantasyonu")
                        .version("v1.0"));
    }
}
