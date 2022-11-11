package it.iad.demofabrick.exception;

import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class ValidateError {
	private String status;
	
	private Date timestamp;
	
	private Map<String, String> errors;
		
}
