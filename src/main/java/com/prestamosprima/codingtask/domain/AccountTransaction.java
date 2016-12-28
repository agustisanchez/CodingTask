package com.prestamosprima.codingtask.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "account_transaction")
public class AccountTransaction extends BaseEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@Enumerated(EnumType.STRING)
	private TransactionType type;

	private BigDecimal amount;

	public AccountTransaction() {
	}

	public AccountTransaction(Account account, TransactionType type, BigDecimal amount) {
		super();
		this.account = account;
		this.type = type;
		this.amount = amount;
	}

	public Account getAccount() {
		return account;
	}

	public TransactionType getType() {
		return type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Placement [id=" + getId() + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()="
				+ getUpdateDate() + ", amount=" + amount + "]";
	}

}
