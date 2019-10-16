package com.queryinterface.sapioscpworkshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal error while calling S/4 HANA")
public class S4ServerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7030701128154056460L;

	public S4ServerException() {
		super();
	}
	
	public S4ServerException(Throwable cause) {
		super(cause);
	}
}
