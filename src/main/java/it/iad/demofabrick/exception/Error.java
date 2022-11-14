package it.iad.demofabrick.exception;

import lombok.Data;

/**
 * @author acolone
 * 
 */

@Data
public class Error {
	
	private String code;
	
	private String description;
	
	private String params;

}
