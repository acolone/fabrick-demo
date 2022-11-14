package it.iad.demofabrick.exception;

import java.util.List;

import lombok.Data;

/**
 * @author acolone
 * 
 */

@Data
public class ErrorMessage {
	
	private String status;
	
	private List<Error> errors;

}
