package it.iad.demofabrick.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LegalPersonBeneficiary implements Serializable{
	private static final long serialVersionUID = -4469832743203092484L;
	
	@NotNull(message="fiscalCode obbligatorio")
	private String fiscalCode;
	
	private String legalRepresentativeFiscalCode;
}
