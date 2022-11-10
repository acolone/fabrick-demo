package it.iad.demofabrick.exception;

import org.springframework.http.HttpStatus;


public class BalanceException extends RuntimeException {
	private static final long serialVersionUID = -29544484236117427L;
	
	private String errorCode;
	private HttpStatus status;

	public BalanceException(String errorCode, HttpStatus httpStatus, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.status = httpStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
