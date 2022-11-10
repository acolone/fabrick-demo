package it.iad.demofabrick.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Creditor implements Serializable{
	private static final long serialVersionUID = 2631359289594154854L;
	
	private String name;
    private Account account;
    private Address address;
}
