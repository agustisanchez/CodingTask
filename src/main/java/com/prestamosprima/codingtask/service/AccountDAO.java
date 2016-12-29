package com.prestamosprima.codingtask.service;

import com.prestamosprima.codingtask.domain.Account;
import com.prestamosprima.codingtask.domain.Customer;

public interface AccountDAO extends BaseDAO<Account> {

	Account findOneByCustomerAndId(Customer customer, Long id);

}
