package com.prestamosprima.codingtask.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prestamosprima.codingtask.domain.Account;

public class AccountDTO {

	private Account account;

	public AccountDTO(@JsonProperty("balance") Account account) {
		super();
		this.account = account;
	}

	public BigDecimal getBalance() {
		return account.getBalance();
	}
}
