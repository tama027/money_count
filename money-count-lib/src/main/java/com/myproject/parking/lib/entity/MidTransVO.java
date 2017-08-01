package com.myproject.parking.lib.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "customer_details", "credit_card", "item_details",
		"transaction_details" })
public class MidTransVO {

	@JsonProperty("customer_details")
	private CustomerDetails customerDetails;
	@JsonProperty("credit_card")
	private CreditCard creditCard;
	@JsonProperty("item_details")
	private List<ItemDetail> itemDetails = null;
	@JsonProperty("transaction_details")
	private TransactionDetails transactionDetails;
	@JsonProperty("custom_field1")
	private String sessionKey;
	

	@JsonProperty("customer_details")
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	@JsonProperty("customer_details")
	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	@JsonProperty("credit_card")
	public CreditCard getCreditCard() {
		return creditCard;
	}

	@JsonProperty("credit_card")
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@JsonProperty("item_details")
	public List<ItemDetail> getItemDetails() {
		return itemDetails;
	}

	@JsonProperty("item_details")
	public void setItemDetails(List<ItemDetail> itemDetails) {
		this.itemDetails = itemDetails;
	}

	@JsonProperty("transaction_details")
	public TransactionDetails getTransactionDetails() {
		return transactionDetails;
	}

	@JsonProperty("transaction_details")
	public void setTransactionDetails(TransactionDetails transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	@JsonProperty("custom_field1")
	public String getSessionKey() {
		return sessionKey;
	}

	@JsonProperty("custom_field1")
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	

}
