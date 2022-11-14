package it.iad.demofabrick.exception;

import java.util.Date;
import java.util.Map;

import lombok.Data;

/**
 * @author acolone
 * 
 */

@Data
public class ValidateError {
	private String status;
	
	private Date timestamp;
	
	private Map<String, String> errors;
		
}
