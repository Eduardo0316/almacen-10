package com.eduardo.almacen.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration 
public class OpenApiConfig {
    @Bean 
    public OpenAPI openApi(){
        return new OpenAPI().info(new Info()
            .title("API de almacen")
            .version("1.0.0")
            .description("Api para la gestion del inventario de productos"));
    } 
}
