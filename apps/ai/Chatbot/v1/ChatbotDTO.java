package com.hawaiki.nrms.service.dto;

import lombok.Getter;
import lombok.Setter;

public class ChatbotDTO {

    @Getter
    @Setter
    private String response;

    public ChatbotDTO() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
