
package com.bwd.nms.service.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {
    @JsonProperty("accessToken")
    private String accessToken;


    public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}



}
