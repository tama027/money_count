package com.myproject.parking.lib.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "save_card", "secure" })
public class CreditCard {

	@JsonProperty("save_card")
	private Boolean saveCard;
	@JsonProperty("secure")
	private Boolean secure;

	@JsonProperty("save_card")
	public Boolean getSaveCard() {
		return saveCard;
	}

	@JsonProperty("save_card")
	public void setSaveCard(Boolean saveCard) {
		this.saveCard = saveCard;
	}

	@JsonProperty("secure")
	public Boolean getSecure() {
		return secure;
	}

	@JsonProperty("secure")
	public void setSecure(Boolean secure) {
		this.secure = secure;
	}

}
