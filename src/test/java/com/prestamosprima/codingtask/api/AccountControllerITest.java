package com.prestamosprima.codingtask.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.prestamosprima.codingtask.app.WebSecurityConfig;
import com.prestamosprima.codingtask.service.AccountDAO;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
@ContextConfiguration(locations = "classpath:test-applicationContext.xml", classes = WebSecurityConfig.class)
public class AccountControllerITest {

	@Autowired
	private AccountDAO dao;

	@Autowired
	private MockMvc mvc;

	@Test
	@Transactional
	public void accountCreated() throws Exception {

		String location = this.mvc
				.perform(post("/account").with(httpBasic("user", "password")).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn().getResponse().getHeader("location");

		Assert.assertTrue("Empty location header", StringUtils.isNotBlank(location));
		int idIdx = location.lastIndexOf('/');
		Assert.assertTrue("Malformed location URL", idIdx != -1);

		String locationId = location.substring(idIdx + 1);

		Assert.assertNotNull("Account not found", dao.findOne(new Long(locationId)));

	}

}