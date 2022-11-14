package it.iad.demofabrick.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @author acolone
 * 
 */

@Data
public class Debtor implements Serializable{
	private static final long serialVersionUID = 522384731532025909L;
	
	private String name;
    private Account account;
}
