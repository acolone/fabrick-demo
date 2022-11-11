package it.iad.demofabrick.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import it.iad.demofabrick.exception.BalanceException;
import it.iad.demofabrick.exception.MessageError;
import it.iad.demofabrick.exception.ValidateError;

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
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ValidateError> argumentNotValidExceptionHandler (MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
        });
        
        ValidateError errorsValidate = new ValidateError();
        errorsValidate.setStatus("KO");
        errorsValidate.setTimestamp(new Date());
        errorsValidate.setErrors(errors);
        return new ResponseEntity<ValidateError>(errorsValidate,HttpStatus.BAD_REQUEST);
    }

}
