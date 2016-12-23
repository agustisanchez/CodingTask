package com.prestamosprima.codingtask.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.prestamosprima.codingtask.domain.Account;
import com.prestamosprima.codingtask.domain.AccountTransaction;
import com.prestamosprima.codingtask.domain.TransactionType;

@Service
public class AccountService {

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private AccountTransactionDAO placementDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public AccountDTO findById(Long id) {
		Account account = accountDAO.findOne(id);

		List<TransactionResponseDTO> statement = placementDAO.findTop10ByAccountOrderByCreateDateDesc(account)
				.map(i -> new TransactionResponseDTO(i)).collect(Collectors.toList());

		return (account == null ? null : new AccountDTO(account, statement)); // Use
		// optional?
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Long create() {
		Account account = accountDAO.save(new Account());
		return account.getId();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Long createTransaction(Long accountId, TransactionRequestDTO requestDTO)
			throws AmountMustBePositiveException, AccountNotFoundException, NotEnoughFundsException {

		BigDecimal amount = requestDTO.getAmount();
		TransactionType type = requestDTO.getType();

		if (amount.doubleValue() < 0.0) {
			throw new AmountMustBePositiveException();
		}

		Account account = accountDAO.findOne(accountId);
		if (account == null) {
			throw new AccountNotFoundException();
		}

		switch (type) {
		case DEPOSIT:
			account.setBalance(account.getBalance().add(amount));
			break;
		case WITHDRAWAL:
			account.setBalance(account.getBalance().subtract(amount));
			break;
		default:
			throw new IllegalArgumentException("Unknown transaction type " + type);
		}

		if (account.getBalance().doubleValue() < 0) {
			throw new NotEnoughFundsException();
		}

		AccountTransaction newPlacement = placementDAO.save(new AccountTransaction(account, type, amount));
		accountDAO.save(account);
		return newPlacement.getId();
	}

}
