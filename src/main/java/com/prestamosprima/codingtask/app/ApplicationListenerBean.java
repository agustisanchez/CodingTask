package com.prestamosprima.codingtask.app;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.prestamosprima.codingtask.domain.Account;
import com.prestamosprima.codingtask.domain.Customer;
import com.prestamosprima.codingtask.service.AccountDAO;
import com.prestamosprima.codingtask.service.CustomerDAO;

@Component
public class ApplicationListenerBean implements ApplicationListener {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private CustomerDAO customerDAO;

	private AccountDAO accountDAO;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
			customerDAO = applicationContext.getBean(CustomerDAO.class);
			accountDAO = applicationContext.getBean(AccountDAO.class);
			mayCreateDemoUser();
		}
	}

	@Transactional
	public void mayCreateDemoUser() {
		if (customerDAO.findByName("user") == null) {
			Customer customer1 = customerDAO.save(new Customer("user", "password"));
			logger.warn("Created demo user with id '{}'.", customer1.getId());
		}
		if (customerDAO.findByName("user2") == null) {
			Customer customer2 = customerDAO.save(new Customer("user2", "password2"));
			logger.warn("Created demo user with id '{}'.", customer2.getId());
		}

		Customer accountCustomer = customerDAO.findAll().get(0);

		List<Account> accounts = accountDAO.findAll();
		for (Account account : accounts) {
			if (account.getCustomer() == null) {
				account.setCustomer(accountCustomer);
				accountDAO.save(account);
			}
		}
	}
}