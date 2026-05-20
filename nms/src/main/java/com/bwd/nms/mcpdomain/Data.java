/**
 *
 */
package com.bwd.nms.mcpdomain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


@JsonInclude(Include.NON_NULL)
public class Data implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("attributes")
    private Attributes attributes;

	public Attributes getAttributes() {
		return attributes;
	}
	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

}
