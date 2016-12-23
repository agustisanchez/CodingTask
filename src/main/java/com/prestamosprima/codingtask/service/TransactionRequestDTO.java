package com.prestamosprima.codingtask.service;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prestamosprima.codingtask.domain.TransactionType;

public class TransactionRequestDTO {

	private BigDecimal amount;

	private TransactionType type;

	@JsonCreator
	public TransactionRequestDTO(@JsonProperty("amount") BigDecimal amount) {
		super();
		this.amount = amount;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public TransactionType getType() {
		return type;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
