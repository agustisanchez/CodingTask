package com.prestamosprima.codingtask.service;

import java.util.stream.Stream;

import com.prestamosprima.codingtask.domain.Account;
import com.prestamosprima.codingtask.domain.AccountTransaction;

public interface PlacementDAO extends BaseDAO<AccountTransaction> {

	Stream<AccountTransaction> findTop10ByAccountOrderByCreateDateDesc(Account account);
}
