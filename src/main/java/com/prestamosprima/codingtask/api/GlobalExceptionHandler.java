package com.prestamosprima.codingtask.api;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.prestamosprima.codingtask.service.AccountNotFoundException;
import com.prestamosprima.codingtask.service.AmountMustBePositiveException;
import com.prestamosprima.codingtask.service.BusinessException;
import com.prestamosprima.codingtask.service.DuplicateCustomerEmailException;
import com.prestamosprima.codingtask.service.DuplicateCustomerUsernameException;
import com.prestamosprima.codingtask.service.ExceptionDTO;
import com.prestamosprima.codingtask.service.NotEnoughFundsException;
import com.prestamosprima.codingtask.service.PasswordsDoNotMatchException;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static Map<Class<? extends Exception>, ExceptionMapping> excMap = new HashMap<>();

	static {
		excMap.put(PasswordsDoNotMatchException.class,
				new ExceptionMapping("passwords.do.not.match", HttpStatus.BAD_REQUEST));
		excMap.put(DuplicateCustomerEmailException.class,
				new ExceptionMapping("duplicate.customer.email", HttpStatus.BAD_REQUEST));
		excMap.put(DuplicateCustomerUsernameException.class,
				new ExceptionMapping("duplicate.customer.username", HttpStatus.BAD_REQUEST));
		excMap.put(NotEnoughFundsException.class, new ExceptionMapping("not.enough.funds", HttpStatus.BAD_REQUEST));
		excMap.put(AmountMustBePositiveException.class,
				new ExceptionMapping("amount.must.be.positive", HttpStatus.BAD_REQUEST));
		excMap.put(AccountNotFoundException.class, new ExceptionMapping("account.not.found", HttpStatus.NOT_FOUND));
		excMap.put(Exception.class, new ExceptionMapping("system.error", HttpStatus.INTERNAL_SERVER_ERROR));
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionDTO> handleException(Exception e) {

		if (!(e instanceof BusinessException)) {
			logger.error("API LEVEL SYSTEM EXCEPTION", e);
		} else if (logger.isTraceEnabled()) {
			logger.trace("API LEVEL BUSINESS EXCEPTION", e);
		}

		ExceptionMapping mapping = excMap.get(e.getClass());
		if (mapping == null) {
			mapping = excMap.get(Exception.class);
		}
		return new ResponseEntity<>(new ExceptionDTO(mapping.getCode()), mapping.getStatus());
	}

	// More can be added
	// @ExceptionHandler(value = Exception.class)
	// public String handleException2(Exception e) {
	// return e.getMessage();
	// }

}