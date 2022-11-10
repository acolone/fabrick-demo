package it.iad.demofabrick.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class LegalPersonBeneficiary implements Serializable{
	private static final long serialVersionUID = -4469832743203092484L;
	
	private String fiscalCode;
	private String legalRepresentativeFiscalCode;
}
