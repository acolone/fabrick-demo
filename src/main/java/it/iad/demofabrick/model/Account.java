package it.iad.demofabrick.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Account implements Serializable{
	private static final long serialVersionUID = 7163033598436268049L;
	
	private String accountCode;
    private String bicCode;
}
