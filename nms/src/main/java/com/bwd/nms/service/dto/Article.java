package com.bwd.nms.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Article {

    @JsonProperty("Subject")
    private String subject;
    @JsonProperty("Body")
    private String body;
    @JsonProperty("ContentType")
    private String contentType;

    @JsonProperty("ArticleSend")
    private String articleSend = "0";

    @JsonProperty("From")
    private String from = "";

    @JsonProperty("To")
    private String to = "";

    public String getArticleSend() {
        return articleSend;
    }

    public void setArticleSend(String articleSend) {
        this.articleSend = articleSend;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
