package com.xingyu.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * swagger configuration
 *
 * @author xingyu ji
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Address Book Management")
                        .description("Address Book Management")
                        .version("1.0")
                ).components(new Components()
                        .addSecuritySchemes("Authorization", new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name("apiToken"))
                ).addSecurityItem(
                        new SecurityRequirement().addList("Authorization", Arrays.asList("global", "accessEverything"))
                );
    }
}