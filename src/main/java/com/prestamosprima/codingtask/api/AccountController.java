package com.prestamosprima.codingtask.api;

import java.net.URI;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prestamosprima.codingtask.service.AccountDTO;
import com.prestamosprima.codingtask.service.AccountNotFoundException;
import com.prestamosprima.codingtask.service.AccountService;
import com.prestamosprima.codingtask.service.AmountMustBePositiveException;
import com.prestamosprima.codingtask.service.NotEnoughFundsException;
import com.prestamosprima.codingtask.service.TransactionRequestDTO;

@RestController
@RequestMapping(value = "/account", produces = "application/json")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public AccountDTO getAccount(@PathVariable("id") Long id, Principal principal) throws AccountNotFoundException {
		System.out.println(principal);
		return accountService.findAccountById(id);
	}

	@RequestMapping(value = "{id}/transaction", method = RequestMethod.POST)
	public ResponseEntity<?> deposit(@PathVariable("id") Long accountId, @RequestBody TransactionRequestDTO requestDTO)
			throws AmountMustBePositiveException, AccountNotFoundException, NotEnoughFundsException {
		Long trxId = accountService.createTransaction(accountId, requestDTO);
		return toLocation(trxId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create() {
		Long id = accountService.createAccount();
		return toLocation(id);
	}

	private ResponseEntity<?> toLocation(Long id) {
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(location).build();
	}

}
