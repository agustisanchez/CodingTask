package com.prestamosprima.codingtask.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prestamosprima.codingtask.service.AccountNotFoundException;
import com.prestamosprima.codingtask.service.AccountService;

@RestController
@RequestMapping(value = "/register", produces = "application/json")
public class RegistrationController {

	@Autowired
	private AccountService accountService;

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public void getAccount() throws AccountNotFoundException {
		System.out.println("OK");
	}

}
