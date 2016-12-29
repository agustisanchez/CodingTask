package com.prestamosprima.codingtask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.prestamosprima.codingtask.domain.Customer;

@Component
public class CustomerService {

	@Autowired
	private CustomerDAO customerDAO;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Customer registerCustomer(CustomerRegistrationDTO dto)
			throws PasswordsDoNotMatchException, DuplicateCustomerUsernameException, DuplicateCustomerEmailException {

		if (!dto.getPassword().equals(dto.getRetypedPassword())) {
			throw new PasswordsDoNotMatchException();
		}

		// TODO Check for insecured passwords

		if (customerDAO.findByName(dto.getUsername()) != null) {
			throw new DuplicateCustomerUsernameException();
		}

		if (customerDAO.findByEmail(dto.getEmail()) != null) {
			throw new DuplicateCustomerEmailException();
		}

		Customer customer = customerDAO.save(new Customer(dto.getUsername(), dto.getEmail(), dto.getPassword()));

		dto = null; // Hints garbage collection

		customer.setPassword(null); // Security

		return customer;
	}

}
