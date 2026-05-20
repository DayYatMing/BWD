/**
 *
 */
package com.bwd.cms.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author akshat.tandon
 *
 */
public class Article {

    @JsonProperty("From")
    private String from;

    @JsonProperty("To")
    private String to;

    @JsonProperty("Subject")
    private String subject;

    @JsonProperty("Body")
    private String body;

    @JsonProperty("IsVisibleForCustomer")
    private String isVisibleForCustomer;

    @JsonProperty("ChangeTime")
    private String changeTime;

    public String getIsVisibleForCustomer() {
        return isVisibleForCustomer;
    }

    public void setIsVisibleForCustomer(String isVisibleForCustomer) {
        this.isVisibleForCustomer = isVisibleForCustomer;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
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
}
