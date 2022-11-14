package it.iad.demofabrick.model;

import lombok.Data;

/**
 * @author acolone
 * 
 */

@Data
public class Result<T> {
	private String status;
	
	private String[] error;
	
	private T payload;
}
