package it.iad.demofabrick.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @author acolone
 * 
 */

@Data
public class Type implements Serializable {
	private static final long serialVersionUID = 5319236003175536464L;
	
	private String enumeration;
    private String value;
}
