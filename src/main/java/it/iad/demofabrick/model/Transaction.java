package it.iad.demofabrick.model;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author acolone
 * 
 */

@Data
public class Transaction {
	private String transactionId;
	private String operationId;
	private String accountingDate;
	private String valueDate;
	private Type type;
	private BigDecimal amount;
	private String currency;
	private String description;
}
