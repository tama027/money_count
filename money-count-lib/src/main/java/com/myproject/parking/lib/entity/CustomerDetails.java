package com.myproject.parking.lib.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "billing_address", "email", "first_name", "phone",
		"shipping_address" })
public class CustomerDetails {

	@JsonProperty("billing_address")
	private BillingAddress billingAddress;
	@JsonProperty("email")
	private String email;
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("phone")
	private String phone;
	@JsonProperty("shipping_address")
	private ShippingAddress shippingAddress;

	@JsonProperty("billing_address")
	public BillingAddress getBillingAddress() {
		return billingAddress;
	}

	@JsonProperty("billing_address")
	public void setBillingAddress(BillingAddress billingAddress) {
		this.billingAddress = billingAddress;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("first_name")
	public String getFirstName() {
		return firstName;
	}

	@JsonProperty("first_name")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonProperty("phone")
	public String getPhone() {
		return phone;
	}

	@JsonProperty("phone")
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonProperty("shipping_address")
	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	@JsonProperty("shipping_address")
	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	

}
