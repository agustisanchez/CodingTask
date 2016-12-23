package com.prestamosprima.codingtask.api;

import org.springframework.http.HttpStatus;

public class ExceptionMapping {

	private String code;

	private HttpStatus status;

	public ExceptionMapping(String code, HttpStatus httpStatus) {
		super();
		this.code = code;
		this.status = httpStatus;
	}

	public String getCode() {
		return code;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
