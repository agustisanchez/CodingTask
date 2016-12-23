package com.prestamosprima.codingtask.service;

public class ExceptionDTO {

	private String errorCode;

	public ExceptionDTO(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
