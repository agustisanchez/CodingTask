package com.prestamosprima.codingtask.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prestamosprima.codingtask.domain.TransactionType;
import com.prestamosprima.codingtask.service.AccountDTO;
import com.prestamosprima.codingtask.service.AccountService;
import com.prestamosprima.codingtask.service.TransactionRequestDTO;

@RestController
@RequestMapping(value = "/account", produces = "application/json")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") Long id) {
		AccountDTO accountDTO = accountService.findById(id);
		if (accountDTO == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(accountDTO, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "{id}/deposit", method = RequestMethod.POST)
	public ResponseEntity<?> deposit(@PathVariable("id") Long accountId, @RequestBody TransactionRequestDTO requestDTO)
			throws Exception {
		requestDTO.setType(TransactionType.DEPOSIT);
		Long trxId = accountService.createTransaction(accountId, requestDTO);
		return toLocation(trxId);
	}

	@RequestMapping(value = "{id}/withdrawal", method = RequestMethod.POST)
	public ResponseEntity<?> withdraw(@PathVariable("id") Long accountId, @RequestBody TransactionRequestDTO requestDTO)
			throws Exception {
		requestDTO.setType(TransactionType.WITHDRAWAL);
		Long trxId = accountService.createTransaction(accountId, requestDTO);
		return toLocation(trxId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create() {
		Long id = accountService.create();
		return toLocation(id);
	}

	private ResponseEntity<?> toLocation(Long id) {
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(location).build();
	}

}
