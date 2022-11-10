package it.iad.demofabrick.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class Balance implements Serializable{
	private static final long serialVersionUID = 7356822865469507505L;

	private Date date;
	
	private BigDecimal balance;
	
	private BigDecimal availableBalance;
	
	private String currency;

}
