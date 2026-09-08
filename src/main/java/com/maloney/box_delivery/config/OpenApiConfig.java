package com.maloney.box_delivery.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Box Tracking API",
                version = "1.0.0",
                description = """
                        RESTful API for managing boxes and the items loaded into them.

                        The API provides functionality for creating and retrieving boxes,
                        loading items, checking battery levels, and retrieving available boxes.

                        It enforces business rules including weight limits, minimum battery
                        requirements, unique tracker IDs, and unique item codes.
                        """
        )
)
public class OpenApiConfig {
}