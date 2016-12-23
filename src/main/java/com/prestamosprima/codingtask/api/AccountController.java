package com.prestamosprima.codingtask.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prestamosprima.codingtask.dto.AccountDTO;
import com.prestamosprima.codingtask.dto.PlacementDTO;
import com.prestamosprima.codingtask.service.AccountService;

@RestController
@RequestMapping(value = "/account", produces = "application/json")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public AccountDTO getBalance(@PathVariable("id") Long id) {
		return accountService.findById(id);
	}

	@RequestMapping(value = "{id}/deposit", method = RequestMethod.POST)
	public ResponseEntity<?> deposit(@PathVariable("id") Long accountId, @RequestBody PlacementDTO placementDTO)
			throws Exception {
		Long depositId = accountService.deposit(accountId, placementDTO);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(depositId).toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(value = "{id}/withdrawal", method = RequestMethod.POST)
	public ResponseEntity<?> withdraw(@PathVariable("id") Long accountId, @RequestBody PlacementDTO placementDTO)
			throws Exception {
		Long depositId = accountService.withdraw(accountId, placementDTO);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(depositId).toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> post() {
		Long id = accountService.create();
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(location).build();
	}

}
