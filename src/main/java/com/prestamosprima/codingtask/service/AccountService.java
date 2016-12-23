package com.prestamosprima.codingtask.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.prestamosprima.codingtask.domain.Account;
import com.prestamosprima.codingtask.domain.Placement;
import com.prestamosprima.codingtask.dto.AccountDTO;
import com.prestamosprima.codingtask.dto.PlacementDTO;

@Service
public class AccountService {

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private PlacementDAO placementDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public AccountDTO findById(Long id) {
		Account account = accountDAO.findOne(id);
		return (account == null ? null : new AccountDTO(account)); // Use
																	// optional?
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Long create() {
		Account account = accountDAO.save(new Account());
		return account.getId();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Long deposit(Long accountId, PlacementDTO depositDTO) throws Exception {

		BigDecimal amount = depositDTO.getAmount();

		if (amount.doubleValue() <= 0) {
			throw new IllegalArgumentException();
		}

		Account account = accountDAO.findOne(accountId);
		if (account == null) {
			throw new Exception(); // TODO review
		}

		account.setBalance(account.getBalance().add(amount));
		Placement newPlacement = placementDAO.save(new Placement(account, amount));
		accountDAO.save(account);
		return newPlacement.getId();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Long withdraw(Long accountId, PlacementDTO placementDTO) throws Exception {
		BigDecimal amount = placementDTO.getAmount();

		if (amount.doubleValue() <= 0) {
			throw new IllegalArgumentException();
		}

		Account account = accountDAO.findOne(accountId);
		if (account == null) {
			throw new Exception(); // TODO review
		}

		account.setBalance(account.getBalance().subtract(amount));

		if (account.getBalance().doubleValue() < 0) {
			throw new Exception();
		}

		Placement newPlacement = placementDAO.save(new Placement(account, amount));
		accountDAO.save(account);
		return newPlacement.getId();
	}
}
