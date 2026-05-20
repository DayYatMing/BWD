
package com.bwd.nms.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TokenRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonProperty("tenant")
	String tenant;

	@JsonProperty("username")
	String username;

	@JsonProperty("password")
	String password;

	@JsonProperty("grant_type")
	String granttype;

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGranttype() {
		return granttype;
	}

	public void setGranttype(String granttype) {
		this.granttype = granttype;
	}




}
