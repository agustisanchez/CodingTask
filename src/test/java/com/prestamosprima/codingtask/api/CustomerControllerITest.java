package com.prestamosprima.codingtask.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.prestamosprima.codingtask.service.CustomerDAO;

@WebMvcTest(AccountController.class)
public class CustomerControllerITest extends AbstractControllerTest {

	@Autowired
	private CustomerDAO dao;

	@Autowired
	private MockMvc mvc;

	@Test
	@Transactional
	public void customerRegistrationSuccess() throws Exception {

		String location = this.mvc
				.perform(post("/customer").contentType(MediaType.APPLICATION_JSON)
						.content(
								"{\"username\":\"user3\", \"email\":\"user3@nowhere.net\", \"password\":\"asa3e_JJ\", \"retypedPassword\":\"asa3e_JJ\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn().getResponse().getHeader("location");

		String locationId = checkLocationAndExtractId(location);

		Assert.assertNotNull("Customer not found", dao.findByName(locationId));

	}

	@Test
	@Transactional
	public void customeRegistrationFailure_passwordsDoNotMatch() throws Exception {

		String newUser = "user6"; // TODO randomize

		MockHttpServletResponse response = this.mvc
				.perform(post("/customer").contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"" + newUser
								+ "\", \"email\":\"user3@nowhere.net\", \"password\":\"asa3e_JJ\", \"retypedPassword\":\"zzzz\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse();

		// TODO Check error code in JSON response
		// response.getContent....

		Assert.assertNull("Customer found", dao.findByName(newUser));

	}

}
