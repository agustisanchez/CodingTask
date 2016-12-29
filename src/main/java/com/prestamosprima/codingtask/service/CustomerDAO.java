package com.prestamosprima.codingtask.service;

import com.prestamosprima.codingtask.domain.Customer;

public interface CustomerDAO extends BaseDAO<Customer> {

	Customer findByName(String name);

	Customer findByEmail(String email);
}
