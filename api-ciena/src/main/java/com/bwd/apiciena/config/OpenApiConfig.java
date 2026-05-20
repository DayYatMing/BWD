package com.bwd.apiciena.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi bwdApi() {
        return GroupedOpenApi.builder().group("bwd").pathsToMatch("/api/bwd/**", "/api/authenticate").build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("BWD API").version("1.0").description("BWD API for registered users"));
    }
}
