package com.bwd.nms.service;

import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ChatbotService {

    private final WebClient webClient;

    private final Environment env;

    public ChatbotService(WebClient.Builder builder, Environment env) {
        this.env = env;
        this.webClient = builder.baseUrl(env.getProperty("gateway.kong.url") + "/broker_ai").build();
    }

    public Mono<String> getData(String body) {
        return webClient
            .post()
            .uri("")
            .header("apikey", env.getProperty("gateway.kong.apikey"))
            .header("Azure-Search-Endpoint", env.getProperty("azure.search.endpoint"))
            .header("Azure-Search-Apikey", env.getProperty("azure.search.apikey"))
            .header("Azure-Search-Index-Name", env.getProperty("azure.search.index-name"))
            .header("Azure-Search-Api-Version", env.getProperty("azure.search.api-version"))
            .header("Azure-Openai-Endpoint", env.getProperty("azure.openai.endpoint"))
            .header("Azure-Openai-Apikey", env.getProperty("azure.openai.apikey"))
            .header("Azure-Openai-Deployment-Name", env.getProperty("azure.openai.deployment-name"))
            .header("Azure-Openai-Api-Version", env.getProperty("azure.openai.api-version"))
            .header("Azure-Openai-Text-Embedding-Name", env.getProperty("azure.openai.text-embedding-name"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String.class);
    }
}
