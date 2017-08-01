package com.myproject.parking.lib.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "gross_amount", "order_id" })
public class TransactionDetails {

	@JsonProperty("gross_amount")
	private Integer grossAmount;
	@JsonProperty("order_id")
	private String orderId;
	

	@JsonProperty("gross_amount")
	public Integer getGrossAmount() {
		return grossAmount;
	}

	@JsonProperty("gross_amount")
	public void setGrossAmount(Integer grossAmount) {
		this.grossAmount = grossAmount;
	}

	@JsonProperty("order_id")
	public String getOrderId() {
		return orderId;
	}

	@JsonProperty("order_id")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	

}
