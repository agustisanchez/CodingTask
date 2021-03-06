package com.prestamosprima.codingtask.api;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.prestamosprima.codingtask.domain.Account;
import com.prestamosprima.codingtask.service.AccountDAO;

@WebMvcTest(AccountController.class)
public class AccountControllerTest extends AbstractControllerTest {

	@MockBean
	private AccountDAO dao;

	@Autowired
	private MockMvc mvc;

	@Test
	public void accountCreated() throws Exception {

		Account mockAccount = mock(Account.class);
		when(mockAccount.getId()).thenReturn(23L); // TODO randomize
		when(dao.save(any(Account.class))).thenReturn(mockAccount);

		this.mvc.perform(post("/account").with(httpBasic("user", "password")).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(header().string("location", "http://localhost/account/23"));
	}

	@Test
	public void accountNotFound() throws Exception {

		// Return null, account does not exist.
		// when(dao.find... mock return null by default

		String accId = "123"; // TODO randomize
		this.mvc.perform(
				get("/account/" + accId).with(httpBasic("user", "password")).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
