package com.prestamosprima.codingtask.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlacementDTO {

	private BigDecimal amount;

	@JsonCreator
	public PlacementDTO(@JsonProperty("amount") BigDecimal amount) {
		super();
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
