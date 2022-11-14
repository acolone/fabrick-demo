package it.iad.demofabrick.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author acolone
 * 
 */

@Data
public class Amount implements Serializable{
	private static final long serialVersionUID = 4276383092713752396L;
	
	private BigDecimal debtorAmount;
	
	private String debtorCurrency;
	
	private BigDecimal creditorAmount;
	
	private String creditorCurrency;
	
	private String creditorCurrencyDate;
	
	private double exchangeRate;
}
