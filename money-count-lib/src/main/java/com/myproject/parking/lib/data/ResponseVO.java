package com.myproject.parking.lib.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "token", "error_messages" })
public class ResponseVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("token")
	private String token;
	@JsonProperty("error_messages")
	private String errorMessages;
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@JsonProperty("token")
	public String getToken() {
		return token;
	}

	@JsonProperty("token")
	public void setToken(String token) {
		this.token = token;
	}

	@JsonProperty("error_messages")
	public String getErrorMessages() {
		return errorMessages;
	}

	@JsonProperty("error_messages")
	public void setErrorMessages(String errorMessages) {
		this.errorMessages = errorMessages;
	}

	

	

}
