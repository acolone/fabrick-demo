package it.iad.demofabrick.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class Fee implements Serializable{
	private static final long serialVersionUID = 8416102821964382360L;
	
	private String feeCode;
	private String description;
	private BigDecimal amount;
	private String currency;
}
