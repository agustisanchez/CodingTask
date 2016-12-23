package com.prestamosprima.codingtask.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.prestamosprima.codingtask.domain.AccountTransaction;
import com.prestamosprima.codingtask.domain.TransactionType;

public class TransactionResponseDTO {

	private AccountTransaction transaction;

	public TransactionType getType() {
		return transaction.getType();
	}

	public BigDecimal getAmount() {
		return transaction.getAmount();
	}

	public Date getCreateDate() {
		return transaction.getCreateDate();
	}

	public TransactionResponseDTO(AccountTransaction placement) {
		super();
		this.transaction = placement;
	}

}
