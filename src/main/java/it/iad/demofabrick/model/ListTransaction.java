package it.iad.demofabrick.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ListTransaction implements Serializable{
	private static final long serialVersionUID = -541806022603654065L;
	
	private List<Transaction> list;

}
