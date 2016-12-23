package com.prestamosprima.codingtask.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "placement")
public class Placement extends BaseEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	private BigDecimal amount;

	public Placement() {
	}

	public Placement(Account account, BigDecimal amount) {
		super();
		this.account = account;
		this.amount = amount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
