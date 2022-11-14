package it.iad.demofabrick.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author acolone
 * 
 */

@Data
public class Transfer implements Serializable{
	private static final long serialVersionUID = -6933441095927509618L;
	
	@Valid
	private Creditor creditor;
	
	private String executionDate;
	
	private String uri;
	
	@NotBlank(message="description obbligatorio")
    @Size(min = 1, max = 140)	
	private String description;
	
	@NotNull(message="amount obbligatorio")
    @DecimalMin("0.1") 
	private BigDecimal amount;
	
	@NotBlank(message="currency obbligatorio")
	private String currency;
	
	private Boolean isUrgent;
	
	private Boolean isInstant;
	
	private String feeType;
	
	private String feeAccountId;
	
	private TaxRelief taxRelief;
}
