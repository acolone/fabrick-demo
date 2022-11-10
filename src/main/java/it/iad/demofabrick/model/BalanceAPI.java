package it.iad.demofabrick.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class BalanceAPI implements Serializable{
	private static final long serialVersionUID = 3378700423596459255L;
	
	private String status;
	private String[] error;
	private Balance payload;
}
