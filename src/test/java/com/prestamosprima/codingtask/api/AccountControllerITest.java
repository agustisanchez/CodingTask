package com.prestamosprima.codingtask.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.prestamosprima.codingtask.service.AccountDAO;
import com.prestamosprima.codingtask.service.AccountDAO2;

@WebMvcTest(AccountController.class)
public class AccountControllerITest extends AbstractControllerTest {

	@Autowired
	private AccountDAO dao;

	@Autowired
	private AccountDAO2 dao2;

	@Autowired
	private MockMvc mvc;

	@Test
	@Transactional
	public void accountCreated() throws Exception {

		String location = this.mvc
				.perform(post("/account").with(httpBasic("user", "password")).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn().getResponse().getHeader("location");

		String locationId = checkLocationAndExtractId(location);

		Assert.assertNotNull("Account not found", dao2.findOne(new Long(locationId)));

	}

	@Test
	public void accountNotFoundIfBelongsToAnotherUser_attemptGet() throws Exception {

		// Create account with some user
		String location = this.mvc
				.perform(post("/account").with(httpBasic("user", "password")).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn().getResponse().getHeader("location");

		String locationId = checkLocationAndExtractId(location);
		Assert.assertNotNull("Account was not created", dao.findOne(new Long(locationId)));

		// Try to retrieve account with another user

		this.mvc.perform(
				get("/account/" + locationId).with(httpBasic("user2", "password2")).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void accountNotFoundIfBelongsToAnotherUser_attemptDeposit() throws Exception {

		// Create account with some user
		String location = this.mvc
				.perform(post("/account").with(httpBasic("user", "password")).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn().getResponse().getHeader("location");

		String locationId = checkLocationAndExtractId(location);
		Assert.assertNotNull("Account was not created", dao.findOne(new Long(locationId)));

		// Try to perform transaction with another user

		this.mvc.perform(post("/account/" + locationId + "/transaction").contentType(MediaType.APPLICATION_JSON)
				.content("{\"amount\":25.0, \"type\":\"DEPOSIT\"}").with(httpBasic("user2", "password2"))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

}
