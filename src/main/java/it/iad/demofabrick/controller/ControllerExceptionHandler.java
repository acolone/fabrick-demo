package it.iad.demofabrick.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import it.iad.demofabrick.exception.BalanceException;
import it.iad.demofabrick.exception.MessageError;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(BalanceException.class)
	public ResponseEntity<MessageError> balanceException(BalanceException ex, WebRequest request) {
		MessageError messageError = new MessageError();
		messageError.setStatusCode(ex.getErrorCode());
		messageError.setMessage(ex.getMessage());
		messageError.setTimestamp(new Date());
        return new ResponseEntity<MessageError>(messageError,ex.getStatus());
	}
	
}
