package com.prestamosprima.codingtask.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prestamosprima.codingtask.domain.Customer;
import com.prestamosprima.codingtask.service.CustomerRegistrationDTO;
import com.prestamosprima.codingtask.service.CustomerService;
import com.prestamosprima.codingtask.service.DuplicateCustomerEmailException;
import com.prestamosprima.codingtask.service.DuplicateCustomerUsernameException;
import com.prestamosprima.codingtask.service.PasswordsDoNotMatchException;

@RestController
@RequestMapping(value = "/customer", produces = "application/json")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationDTO dto)
			throws PasswordsDoNotMatchException, DuplicateCustomerUsernameException, DuplicateCustomerEmailException {
		Customer customer = customerService.registerCustomer(dto);
		// TODO Username or id???
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
				.buildAndExpand(customer.getName()).toUri();
		return ResponseEntity.created(location).build();

	}
}
