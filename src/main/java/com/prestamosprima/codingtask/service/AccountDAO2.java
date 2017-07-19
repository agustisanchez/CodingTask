package com.prestamosprima.codingtask.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.mysema.query.jpa.impl.JPAQuery;
import com.prestamosprima.codingtask.domain.Account;
import com.prestamosprima.codingtask.domain.QAccount;

@Component
public class AccountDAO2 {

	@PersistenceContext
	private EntityManager entityManager;

	public Account findOne(Long id) {
		QAccount account = QAccount.account;
		JPAQuery query = new JPAQuery(entityManager);
		return query.from(account).where(account.id.eq(id)).uniqueResult(account);
	}

}
