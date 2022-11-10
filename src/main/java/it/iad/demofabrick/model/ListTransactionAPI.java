package it.iad.demofabrick.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ListTransactionAPI implements Serializable{
	private static final long serialVersionUID = -5778736057109074059L;
	
	private String status;
    private ListTransaction payload;

}
