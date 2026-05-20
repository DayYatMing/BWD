package com.bwd.nms.web.rest;

import com.bwd.nms.service.ChatbotService;
import com.bwd.nms.service.dto.ChatbotDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing Site.
 */
@RestController
@RequestMapping("/api")
public class ChatbotResource {

    private final Logger log = LoggerFactory.getLogger(ChatbotResource.class);

    private final ChatbotService chatbotService;

    public ChatbotResource(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/chatbot/response")
    public Mono<String> replyMessage(@Valid @RequestBody ChatbotDTO chatbotDTO) {
        log.info("REST request to chat with message : {}", chatbotDTO.getResponse());

        if (chatbotDTO.getResponse() != null || !chatbotDTO.getResponse().isEmpty()) {
            String body = "{" +
                "\"message\":\"" + chatbotDTO.getResponse() + "\", " +
                "\"context\":\"" + chatbotDTO.getContext() + "\", " +
                "\"method\":\"" + chatbotDTO.getMethod() + "\"" +
                "}";
            return chatbotService.getData(body);
        }

        return Mono.empty();
    }
}
