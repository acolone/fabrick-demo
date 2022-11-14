package it.iad.demofabrick.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import it.iad.demofabrick.exception.BalanceException;
import it.iad.demofabrick.exception.Error;
import it.iad.demofabrick.exception.ErrorMessage;
import it.iad.demofabrick.exception.ValidateError;

/**
 * @author acolone
 * 
 */

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(BalanceException.class)
	public ResponseEntity<ErrorMessage> balanceException(BalanceException ex, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setStatus("KO");
		List<Error> err = new ArrayList<Error>();
		Error error = new Error();
		error.setCode(ex.getErrorCode());
		error.setDescription(ex.getMessage());
		error.setParams("");
		err.add(error);
		errorMessage.setErrors(err);
		return new ResponseEntity<ErrorMessage>(errorMessage,ex.getStatus());
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
