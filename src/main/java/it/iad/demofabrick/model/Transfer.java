package it.iad.demofabrick.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class Transfer implements Serializable{
	private static final long serialVersionUID = -6933441095927509618L;
	
	private Creditor creditor;
	private String executionDate;
	private String uri;
	private String description;
	private BigDecimal amount;
	private String currency;
	private Boolean isUrgent;
	private Boolean isInstant;
	private String feeType;
	private String feeAccountId;
	private TaxRelief taxRelief;
}
