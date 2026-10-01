package com.hawaiki.nrms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hawaiki.nrms.service.dto.ChatbotDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ChatbotResource {

    private final Logger log = LoggerFactory.getLogger(ChatbotResource.class);

    public ChatbotResource() {

    }

    @GetMapping("/chatbot/response")
    @Timed
    public ResponseEntity<ChatbotDTO> getResponse(@RequestParam String message) {
        log.info("REST request to get message : {}", message);
        ChatbotDTO cbotDTO = new ChatbotDTO();
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        cbotDTO.setResponse(sb.toString());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cbotDTO));
    }
}
