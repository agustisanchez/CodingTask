package com.prestamosprima.codingtask.service;

import java.math.BigDecimal;
import java.util.List;

import com.prestamosprima.codingtask.domain.Account;

public class AccountDTO {

	private Account account;

	private List<TransactionResponseDTO> statement;

	public AccountDTO(Account account, List<TransactionResponseDTO> statement) {
		super();
		this.account = account;
		this.statement = statement;
	}

	public BigDecimal getBalance() {
		return account.getBalance();
	}

	public List<TransactionResponseDTO> getStatement() {
		return statement;
	}
}
