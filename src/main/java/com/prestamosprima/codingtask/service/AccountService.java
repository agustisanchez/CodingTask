package com.prestamosprima.codingtask.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.prestamosprima.codingtask.domain.Account;
import com.prestamosprima.codingtask.domain.AccountTransaction;
import com.prestamosprima.codingtask.domain.Customer;
import com.prestamosprima.codingtask.domain.TransactionType;

@Service
public class AccountService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private AccountTransactionDAO accountTransactionDAO;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AccountDTO findAccountById(Long id) throws AccountNotFoundException {

		Customer customer = fetchCustomer();

		// Account must exist and must belong to customer

		Account account = fetchCustomerAccount(id, customer);

		List<TransactionResponseDTO> statement = accountTransactionDAO.findTop10ByAccountOrderByCreateDateDesc(account)
				.map(i -> new TransactionResponseDTO(i)).collect(Collectors.toList());

		return new AccountDTO(account, statement);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Long createAccount() {

		Customer customer = fetchCustomer();

		Account account = accountDAO.save(new Account(customer));
		return account.getId();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Long createTransaction(Long accountId, TransactionRequestDTO requestDTO)
			throws AmountMustBePositiveException, AccountNotFoundException, NotEnoughFundsException {

		Customer customer = fetchCustomer();

		BigDecimal amount = requestDTO.getAmount();
		TransactionType type = requestDTO.getType();

		if (amount.doubleValue() < 0.0) {
			throw new AmountMustBePositiveException();
		}

		// Account must exist and must belong to customer

		Account account = fetchCustomerAccount(accountId, customer);

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

		AccountTransaction newPlacement = accountTransactionDAO.save(new AccountTransaction(account, type, amount));
		accountDAO.save(account);
		return newPlacement.getId();
	}

	private Account fetchCustomerAccount(Long accountId, Customer customer) throws AccountNotFoundException {
		Account account = accountDAO.findOneByCustomerAndId(customer, accountId);
		if (account == null) {
			throw new AccountNotFoundException();
		}
		return account;
	}

	private Customer fetchCustomer() {
		String userName = userName();
		logger.debug("Customer '{}'", userName);

		Customer customer = customerDAO.findByName(userName);

		if (customer == null) {
			throw new RuntimeException("Could not find user '" + userName + "' in database.");
		}
		return customer;
	}

	private String userName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
