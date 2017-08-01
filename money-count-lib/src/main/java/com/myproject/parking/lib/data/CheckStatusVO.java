package com.myproject.parking.lib.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "status_code", "status_message", "transaction_id",
		"masked_card", "order_id", "payment_type", "transaction_time",
		"transaction_status", "fraud_status", "approval_code", "signature_key",
		"bank", "gross_amount" })
public class CheckStatusVO {

	@JsonProperty("status_code")
	private String statusCode;
	@JsonProperty("status_message")
	private String statusMessage;
	@JsonProperty("transaction_id")
	private String transactionId;
	@JsonProperty("masked_card")
	private String maskedCard;
	@JsonProperty("order_id")
	private String orderId;
	@JsonProperty("payment_type")
	private String paymentType;
	@JsonProperty("transaction_time")
	private String transactionTime;
	@JsonProperty("transaction_status")
	private String transactionStatus;
	@JsonProperty("fraud_status")
	private String fraudStatus;
	@JsonProperty("approval_code")
	private String approvalCode;
	@JsonProperty("signature_key")
	private String signatureKey;
	@JsonProperty("bank")
	private String bank;
	@JsonProperty("gross_amount")
	private String grossAmount;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@JsonProperty("status_code")
	public String getStatusCode() {
		return statusCode;
	}

	@JsonProperty("status_code")
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@JsonProperty("status_message")
	public String getStatusMessage() {
		return statusMessage;
	}

	@JsonProperty("status_message")
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@JsonProperty("transaction_id")
	public String getTransactionId() {
		return transactionId;
	}

	@JsonProperty("transaction_id")
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@JsonProperty("masked_card")
	public String getMaskedCard() {
		return maskedCard;
	}

	@JsonProperty("masked_card")
	public void setMaskedCard(String maskedCard) {
		this.maskedCard = maskedCard;
	}

	@JsonProperty("order_id")
	public String getOrderId() {
		return orderId;
	}

	@JsonProperty("order_id")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@JsonProperty("payment_type")
	public String getPaymentType() {
		return paymentType;
	}

	@JsonProperty("payment_type")
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@JsonProperty("transaction_time")
	public String getTransactionTime() {
		return transactionTime;
	}

	@JsonProperty("transaction_time")
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	@JsonProperty("transaction_status")
	public String getTransactionStatus() {
		return transactionStatus;
	}

	@JsonProperty("transaction_status")
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	@JsonProperty("fraud_status")
	public String getFraudStatus() {
		return fraudStatus;
	}

	@JsonProperty("fraud_status")
	public void setFraudStatus(String fraudStatus) {
		this.fraudStatus = fraudStatus;
	}

	@JsonProperty("approval_code")
	public String getApprovalCode() {
		return approvalCode;
	}

	@JsonProperty("approval_code")
	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}

	@JsonProperty("signature_key")
	public String getSignatureKey() {
		return signatureKey;
	}

	@JsonProperty("signature_key")
	public void setSignatureKey(String signatureKey) {
		this.signatureKey = signatureKey;
	}

	@JsonProperty("bank")
	public String getBank() {
		return bank;
	}

	@JsonProperty("bank")
	public void setBank(String bank) {
		this.bank = bank;
	}

	@JsonProperty("gross_amount")
	public String getGrossAmount() {
		return grossAmount;
	}

	@JsonProperty("gross_amount")
	public void setGrossAmount(String grossAmount) {
		this.grossAmount = grossAmount;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
