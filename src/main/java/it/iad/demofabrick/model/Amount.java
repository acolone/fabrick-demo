package it.iad.demofabrick.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Amount implements Serializable{
	private static final long serialVersionUID = 4276383092713752396L;
	
	private int debtorAmount;
	private String debtorCurrency;
	private int creditorAmount;
	private String creditorCurrency;
	private String creditorCurrencyDate;
	private int exchangeRate;
}
