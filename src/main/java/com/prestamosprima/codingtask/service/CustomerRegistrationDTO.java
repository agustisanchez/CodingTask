package com.prestamosprima.codingtask.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerRegistrationDTO {

	private String email;

	private String username;

	private String password;

	private String retypedPassword;

	@JsonCreator
	public CustomerRegistrationDTO(@JsonProperty(value = "username", required = true) String username,
			@JsonProperty(value = "email", required = true) String email,
			@JsonProperty(value = "password", required = true) String password,
			@JsonProperty(value = "retypedPassword", required = true) String retypedPassword) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
		this.retypedPassword = retypedPassword;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getRetypedPassword() {
		return retypedPassword;
	}

}
